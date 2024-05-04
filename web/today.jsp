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
                
                <section class="main-contents">
                    <jsp:include page="layout/view_action.jsp" />
                </section>
                
            </main>
            
            <jsp:include page="layout/task_info.jsp" />
            <jsp:include page="layout/action_form.jsp" />      
            <jsp:include page="layout/mb_navigation.jsp" />
            
            <button class="add-task-floating">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                    <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                </svg>
            </button>
                
            <section class="overlay"></section>
        </section>
    </body>
</html>
