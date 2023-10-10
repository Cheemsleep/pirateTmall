<%--
  Created by IntelliJ IDEA.
  User: ddd
  Date: 2023-07-26
  Time: 下午 12:41
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <script src="js/jquery/2.0.0/Jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="css/back/style.css" rel="stylesheet">

    <script>
        function checkEmpty(id, name) {
            let value = $("#"+id).value();
            if (value.lenth == 0){
                alert(name+"不能为空");
                $("#"+id).focus();
                return false;
            }
            return true;
        }

        function checkNumber(id, name) {
            let value = $("#"+id).value();
            if(value.length == 0) {
                alert(name + "不能为空");
                $("#"+id).focus();
                return false;
            }
            if (isNaN(value)) {
                alert(name + "必须为数字");
                $("#" + id).focus();
                return false;
            }
            return true;
        }

        function checkInt(id, name) {
            let value = $("#" + id).value();
            if (value.length == 0) {
                alert(name + "不能为空");
                $("#" + id).focus();
                return false;
            }
            if (parseInt(value) != value) {
                alert(name + "必须为整数");
                $("#" + id).focus();
                return false;
            }

            return true;
        }

        $(function () {
            $("a").click(function () {
                let deleteLink = $(this).attr("deleteLink"); //attr() 方法设置或返回被选元素的属性值。
                console.log(deleteLink);
                if ("true" == deleteLink) {
                    let confirmDelete = confirm("确认删除吗");
                    if (confirmDelete)
                        return true;
                    return false;
                }
            });
        });
    </script>
</head>
<body>

</body>
</html>
