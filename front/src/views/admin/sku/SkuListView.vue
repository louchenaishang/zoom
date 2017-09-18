<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <template>
      <el-table :data="list" highlight-current-row v-loading="listLoading" style="width: 100%;">
        <el-table-column type="index">
        </el-table-column>
        <el-table-column prop="username" label="姓名" sortable>
        </el-table-column>
        <el-table-column prop="sex" label="性别" :formatter="formatSex" sortable>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" :formatter="formatDate" sortable>
        </el-table-column>
        <el-table-column prop="mobile" label="手机" sortable>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" sortable>
        </el-table-column>
        <el-table-column inline-template :context="_self" label="操作">
	<span>
					<el-button size="small" @click="handleEdit(row)" v-if="isAdmin">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(row)" v-if="isAdmin">删除</el-button>
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
  </section>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
  import util from '@/common/util'
  import Api from '@/api/api'

  export default {
    data() {
      return {
        filters: {
          name: ''
        },
        list: [],
        total: 0,
        page: 1,
        size: 20,
        listLoading: false,
      }
    },
    computed: {
      ...mapGetters([
        'isAdmin'
      ])
    },
    methods: {
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
        this.getList()
      },
      //获取列表
      getList() {
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
          this.list = content
          this.listLoading = false
        })
      },
      //显示编辑界面
      handleEdit: function (row) {
        this.$router.push({path: '/admin/sku/form?type=edit&id='+row.id})
      },
      //显示新增界面
      handleAdd: function () {
        this.$router.push({path: '/admin/sku/form?type=add'})
      },
    },
    mounted() {
      this.getList()
    }
  }
</script>

<style scoped>
</style>
