<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <!--<el-form-item>-->
        <!--<el-button type="primary" @click="handleAdd">新增</el-button>-->
        <!--</el-form-item>-->
      </el-form>
    </el-col>

    <!--列表-->
    <template>
      <el-table :data="users" highlight-current-row v-loading="listLoading" style="width: 100%;">
        <el-table-column type="index" width="60">
        </el-table-column>
        <el-table-column prop="username" label="姓名" width="200" sortable>
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100" :formatter="formatSex" sortable>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="200" :formatter="formatDate" sortable>
        </el-table-column>
        <el-table-column prop="road" label="地址" min-width="180" sortable>
        </el-table-column>
        <el-table-column inline-template :context="_self" label="操作" width="150">
	<span>
					<el-button size="small" @click="handleEdit(row)" v-show="isAdmin">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(row)" v-show="isAdmin">删除</el-button>
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
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="姓名" prop="username">
          <el-input v-model="editForm.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editForm.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="editForm.road"></el-input>
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
  import {mapGetters, mapActions} from 'vuex'
  import util from '../../common/util'
  import Api from '../../api/api'

  export default {
    data() {
      return {
        filters: {
          name: ''
        },
        users: [],
        total: 0,
        page: 1,
        size: 20,
        listLoading: false,
        MALE: 'MALE',
        FEMALE: 'FEMALE',
        editFormVisible: false,//编辑界面显是否显示
        editFormTtile: '编辑',//编辑界面标题
        //编辑界面数据
        editForm: {
          id: 0,
          username: '',
          sex: '男',
          road: ''
        },
        editLoading: false,
        btnEditText: '提 交',
        editFormRules: {
          username: [
            {required: true, message: '请输入姓名', trigger: 'blur'}
          ]
        }

      }
    },
    computed: {
      ...mapGetters([
        'isAdmin'
      ])
    },
    methods: {
      ...mapActions([
        'addToPincipal'
      ]),
      getPrincipal() {
        Api.getPrincipal({}).then(res => {
          this.addToPincipal(res.data)
        })
      },
      //性别显示转换
      formatSex: function (row, column) {
        return row.sex == 'MALE' ? '男' : row.sex == 'FEMALE' ? '女' : row.sex
      },
      //日期格式化
      formatDate: function (row, column) {
        if (row.createTime == null || row.createTime == undefined) {
          return ''
        } else {
          return util.formatDate.format(new Date(row.createTime), 'yyyy-MM-dd hh:mm:ss')
        }
      },
      handleCurrentChange(val) {
        this.page = val
        this.getUsers()
      },
      //获取用户列表
      getUsers() {
        let para = {
          page: this.page - 1,
          size: this.size,
          name: this.filters.name
        }
        this.listLoading = true
        Api.getUserListPage(para).then((res) => {
          console.log('Api.getUserListPage');
          console.log(res)
          let {content, first, last, size, number, numberOfElements, totalElements, totalPages} = res.data
          this.total = totalElements
          this.users = content
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
          Api.removeUser(para).then((res) => {
            _this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success'
            })
            _this.getUsers()
            _this.listLoading = false
          })
        }).catch((error) => {
          _this.listLoading = false
        })
      },
      //显示编辑界面
      handleEdit: function (row) {
        this.editFormVisible = true
        this.editFormTtile = '编辑'
        this.editForm.id = row.id
        this.editForm.username = row.username
        this.editForm.sex = row.sex
        this.editForm.createTime = row.createTime
        this.editForm.road = row.road
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
                  username: _this.editForm.username,
                  sex: _this.editForm.sex,
                  road: _this.editForm.road,
                }
                Api.addUser(para).then((res) => {
                  _this.editLoading = false
                  _this.editFormVisible = false
                  _this.btnEditText = '提 交'
                  _this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  _this.getUsers()
                }).catch((error) => {
                  _this.editLoading = false
                  _this.editFormVisible = false
                  _this.btnEditText = '提 交'
                })

              } else {
                //编辑
                let para = {
                  id: _this.editForm.id,
                  username: _this.editForm.username,
                  sex: _this.editForm.sex,
                  road: _this.editForm.road,
                }
                Api.editUser(para).then((res) => {
                  _this.editLoading = false
                  _this.editFormVisible = false
                  _this.btnEditText = '提 交'
                  _this.$notify({
                    title: '成功',
                    message: '提交成功',
                    type: 'success'
                  })
                  _this.getUsers()
                }).catch((error) => {
                  _this.editLoading = false
                  _this.editFormVisible = false
                  _this.btnEditText = '提 交'
                })

              }

            })

          }
        })

      },
      //显示新增界面
      handleAdd: function () {
        var _this = this

        this.editFormVisible = true
        this.editFormTtile = '新增'

        this.editForm.id = 0
        this.editForm.username = ''
        this.editForm.sex = '男'
        this.editForm.road = ''
      }
    },
    mounted() {
      this.getPrincipal()
      this.getUsers()
    }
  }
</script>

<style scoped>
</style>
