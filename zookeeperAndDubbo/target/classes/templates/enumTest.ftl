<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>枚举上传测试html</title>
</head>
<body>
<form action="/getUserByType">
    <!-- “teacherEnum”为传参对象名称   “high_school_teacher” 为对应的 enum 对应的type值
         通过传递，springmvc根据（“high_school_teacher”）值自动封装为对应的枚举
    -->
    <p>“teacherEnum”为传参对象名称   “high_school_teacher” 为对应的 enum 对应的type值
        通过传递，springmvc根据（“high_school_teacher”）值自动封装为对应的枚举</p>
    <p>例如： university_teacher（大学老师）<br>
        例如： high_school_teacher（大学老师）
    </p>
    <input type="text" name="teacherEnum" value="high_school_teacher">
    <input type="submit" value="提交">
</form>
</body>
</html>