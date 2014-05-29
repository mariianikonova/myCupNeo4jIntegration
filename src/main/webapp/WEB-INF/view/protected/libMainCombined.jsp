<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="combinedPages">

    <tiles:putAttribute name="contentLeft">
        <form>
            <textarea name="editor1" id="editor1" rows="10" cols="80">
                This is my textarea to be replaced with CKEditor.
            </textarea>
            <script>
                CKEDITOR.replace( 'editor1' );
            </script>
        </form>
        <body>
        <div>
            <spring:url var="logoutAction" value="/logout/j_spring_security_logout"/>
            <a href="<c:url value= "${logoutAction}" />" > Logoff</a>
        </div>

    </tiles:putAttribute>

    <tiles:putAttribute name="contentRight">

    </tiles:putAttribute>
</tiles:insertDefinition>