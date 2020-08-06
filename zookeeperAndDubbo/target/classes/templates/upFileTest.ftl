<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>枚举上传测试html</title>
    <link rel="stylesheet" href="/layuiadmin/layui/css/layui.css">
</head>
<body>
<form class="layui-form">

    <a type="button" class="layui-btn" id="test1">
        <i class="layui-icon">&#xe67c;</i>上传图片
    </a>

</form>

<script src="/layuiadmin/layui/layui.js"></script>
<script>

    layui.use(['upload', 'layer'], function () {
        var upload = layui.upload, layer = layui.layer;

        //执行实例
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: '/file/upFile/' //上传接口
            ,done: function(res){
                debugger
                //上传完毕回调
                console.log(res);
                layer.msg("上传文件成功！")
            }
            ,error: function(){
                //请求异常回调
                layer.msg("请求异常回调！")
            }
        });
    })

</script>
</body>
</html>