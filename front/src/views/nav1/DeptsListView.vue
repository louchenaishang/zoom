<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDepts">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <template>
      <el-table :data="depts" highlight-current-row v-loading="listLoading" style="width: 100%;">
        <el-table-column type="index" min-width="60">
        </el-table-column>
        <el-table-column prop="name" label="部门名称" min-width="300" sortable>
        </el-table-column>
        <el-table-column inline-template :context="_self" label="操作" width="150">
	      <span>
					<el-button size="small" @click="handleEdit(row)" v-show="showEdit(row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(row)" v-show="showDel(row)">删除</el-button>
				</span>
        </el-table-column>
      </el-table>
    </template>

    <!--分页-->
    <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="size" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog :title="editFormTtile" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="editForm.name" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取 消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
  import util from '../../common/util'
  import Api from '../../api/api'

  export default {
    data() {
      return {
        filters: {
          name: ''
        },
        depts: [],
        total: 0,
        page: 1,
        size: 20,
        listLoading: false,
        editFormVisible: false,//编辑界面显是否显示
        editFormTtile: '编辑',//编辑界面标题
        //编辑界面数据
        editForm: {
          id: 0,
          name: ''
        },
        editLoading: false,
        btnEditText: '提 交',
        editFormRules: {
          name: [
            {required: true, message: '请输入部门名称', trigger: 'blur'}
          ]
        }

      }
    },
    methods: {
      //是否隐藏编辑按钮
      showEdit (row){
        return true
      },
      //是否隐藏删除按钮
      showDel (row){
        return true
      },

      //日期格式化
      formatDate: function (row, column) {
        if (row.birth == null || row.birth == undefined) {
          return ''
        } else {
          return row.birth.substring(0, 10)
        }
      },
      handleCurrentChange(val) {
        this.page = val
        this.getDepts()
      },
      //获取部门列表
      getDepts() {
        let para = {
          page: this.page - 1,
          size: this.size,
          name: this.filters.name
        }
        this.listLoading = true
        Api.getDeptListPage(para).then((res) => {
          let {body, status} = res.data
          if (status !== 'SUCCESS') {
            this.$notify({
              title: '错误',
              message: body,
              type: 'error'
            })
          } else {
            let {content, first, last, size, number, numberOfElements, totalElements, totalPages} = body
            this.total = totalElements
            this.depts = content
          }
          this.listLoading = false
        })
      },
      //删除
      handleDel: function (row) {
        var _this = this
        this.$confirm('确认删除该记录吗?', '提示', {
          //type: 'warning'
        }).then(() => {
          _this.listLoading = true
          let para = {id: row.id}
          Api.removeDept(para).then((res) => {
            let {body, status} = res.data
            if (status !== 'SUCCESS') {
              this.$notify({
                title: '错误',
                message: body,
                type: 'error'
              })
            } else {
              _this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success'
              })
              _this.getDepts()
            }
            _this.listLoading = false
          })
        }).catch(() => {

        })
      },
      //显示编辑界面
      handleEdit: function (row) {
        this.editFormVisible = true
        this.editFormTtile = '编辑'
        this.editForm.id = row.id
        this.editForm.name = row.name
      },
      //显示新增界面
      handleAdd: function () {
        this.editFormVisible = true
        this.editFormTtile = '新增'
        this.editForm.id = 0
        this.editForm.name = ''
      },
      //编辑 or 新增
      editSubmit: function () {
        var _this = this

        _this.$refs.editForm.validate((valid) => {
          if (valid) {

            _this.$confirm('确认提交吗？', '提示', {}).then(() => {
              _this.editLoading = true
              _this.btnEditText = '提交中'

              if (_this.editForm.id == 0) {
                //新增
                let para = {
                  name: _this.editForm.name
                }
                Api.addDept(para).then((res) => {
                  _this.editLoading = false
                  _this.btnEditText = '提 交'
                  _this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  _this.editFormVisible = false
                  _this.getDepts()
                })
              } else {
                //编辑
                let para = {
                  id: _this.editForm.id,
                  name: _this.editForm.name
                }
                Api.editDept(para).then((res) => {
                  _this.editLoading = false
                  _this.btnEditText = '提 交'
                  _this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  _this.editFormVisible = false
                  _this.getDepts()
                })

              }

            })

          }
        })
        //
      }
    },
    mounted() {
      this.getDepts()
    }
  }
</script>

<style scoped>
</style>
