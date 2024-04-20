<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tổng quan</title>
        <jsp:include page="layout/header.jsp"/>
    </head>
    <body>
        <jsp:include page="layout/page_loader.jsp" />
        <section class="main-layout d-flex">
            <jsp:include page="layout/sidebar.jsp" />
            
            <main>
                <jsp:include page="layout/header_navigation.jsp" />
            </main>
            
            <jsp:include page="layout/task_info.jsp" />
            <jsp:include page="layout/create_task.jsp" />    
            <jsp:include page="layout/mb_navigation.jsp" />
                
            <section class="overlay"></section>
        </section>
    </body>
</html>
