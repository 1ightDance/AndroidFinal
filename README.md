# AndroidFinal
android class final teamwork

# 任务（暂定）
## Activity:
1.登录，
2.主界面（附带searchview） {
  Fragment1:类别选择（RecyclerView）
  Fragment2:展示筛选结果（RecyclerView）
  Fragment3:详情页（可编辑）
  alertDialog：添加类别
}
# 分工
### 涛涛：登录，dao
### 天舒：先写着剩下的


## Dao层接口

#### 类别：ClassifyCurd

> 创建笔记类别

```java
createClassify(Classify type)
return type: long   返回创建的该类别的id
```

> 更新笔记类别信息

```java
updateClassify(Classify type)
return type: boolean     更新成功与否标志
```

> 删除某一个笔记类别

```java
deleteClassify(int typeId)
return type: boolean     删除成功与否标志
```

> 查找全部的笔记类别

```java
findAllClassify()
return type: List<Classify>     笔记类别列表
```

#### 笔记：NoteCurd

> 创建笔记

```java
createNote(Note note)
return type: long   返回创建的该笔记的id
```

> 更新笔记信息

```java
updateNote(Note note)
return type: boolean     更新成功与否标志
```

> 删除笔记信息

```java
deleteNode(int noteId)
return type: boolean     删除成功与否标志
```

> 查询某个类别下的所有笔记

```java
findNoteByClassifyId(String typeId)
return type: List<Note>     笔记列表
```

> 根据笔记修改时间范围查询

```java
findNoteByDate(Date start, Date end)
return type: List<Note>     笔记列表
```

> 根据笔记标题模糊查询

```java
findNoteByTitle(String title)
return type: List<Note>     笔记列表
```
