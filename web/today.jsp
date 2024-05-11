<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="utils.FormatLocalDateTime" %>

<fmt:setLocale value="vi_VN" />
<c:if test="${sessionScope.lang == 'en'}">
    <fmt:setLocale value="en_US" />
</c:if>

<fmt:setBundle basename="languages.lang" var="bundle" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><fmt:message bundle="${bundle}" key="title_today" /></title>
        <jsp:include page="layout/header.jsp"/>
    </head>
    <body>
        <jsp:include page="layout/page_loader.jsp" />
        <section class="main-layout d-flex">
            <jsp:include page="layout/sidebar.jsp" />
            
            <main>
                <jsp:include page="layout/header_navigation.jsp" />
                
                <section class="main-contents today-page">
                    <jsp:include page="layout/view_action.jsp" />
                    
                    <c:set var="todoList" value="${requestScope.TodoList}" />
                    <c:set var="upcomingTodoList" value="${requestScope.Upcoming}" />
                    <c:set var="view" value="${param.view}" />
                    
                    <c:choose>
                        <c:when test="${view == 'grid'}">
                            <div class="tasks-box upcoming mt-2">
                                <div class="task-list-title d-flex align-items-center gap-3">
                                    <span>Sắp tới</span>
                                    <ion-icon class="show-hide-tasks" name="caret-up-outline"></ion-icon>
                                </div>
                                <div class="task-list view-grid mt-2">
                                <c:forEach var="todo" items="${upcomingTodoList}">
                                    <div class="task-box d-flex flex-column justify-content-between" data-id="${todo.id}">
                                        <div class="task-box-header d-flex align-items-start justify-content-between">
                                            <div class="task-box-categories d-flex align-items-center gap-3 me-2">
                                                <c:forEach var="category" items="${todo.categories}">
                                                    <div class="task-box-category d-flex align-items-center gap-2">
                                                        <span class="nav-color-icon" style="background: ${category.iconColor};"></span>
                                                        <span>${category.cateName}</span>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <div class="show-task-inf">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                                    <path d="M12 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zM6 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                                                </svg>
                                            </div>
                                        </div>
                                        <div class="task-box-main">
                                            <div class="task-box-name">
                                                ${todo.title}
                                            </div>
                                            <div class="task-box-des">
                                                ${todo.description}
                                            </div>
                                        </div>
                                        <div class="task-box-time d-flex align-items-center justify-content-between gap-3">
                                            <span>${FormatLocalDateTime.formatOnlyTime(todo.dateToComplete)}</span>
                                            <span>${FormatLocalDateTime.formatOnlyDate(todo.dateToComplete)}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                                </div>
                            </div>
                            
                            <div class="tasks-box today mt-3">
                                <div class="task-list-title d-flex align-items-center gap-3">
                                    <span>Hôm nay</span>
                                    <ion-icon class="show-hide-tasks" name="caret-up-outline"></ion-icon>
                                </div>
                                <div class="task-list view-grid mt-2">
                                <c:forEach var="todo" items="${todoList}">
                                    <div class="task-box d-flex flex-column justify-content-between" data-id="${todo.id}">
                                        <div class="task-box-header d-flex align-items-start justify-content-between">
                                            <div class="task-box-categories d-flex align-items-center gap-3 me-2">
                                                <c:forEach var="category" items="${todo.categories}">
                                                    <div class="task-box-category d-flex align-items-center gap-2">
                                                        <span class="nav-color-icon" style="background: ${category.iconColor};"></span>
                                                        <span>${category.cateName}</span>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <div class="show-task-inf">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                                    <path d="M12 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zM6 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                                                </svg>
                                            </div>
                                        </div>
                                        <div class="task-box-main">
                                            <div class="task-box-name">
                                                ${todo.title}
                                            </div>
                                            <div class="task-box-des">
                                                ${todo.description}
                                            </div>
                                        </div>
                                        <div class="task-box-time d-flex align-items-center justify-content-between gap-3">
                                            <span>${FormatLocalDateTime.formatOnlyTime(todo.dateToComplete)}</span>
                                            <span>${FormatLocalDateTime.formatOnlyDate(todo.dateToComplete)}</span>
                                        </div>
                                    </div>
                                </c:forEach>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="tasks-box upcoming mt-2">
                                <div class="task-list-title d-flex align-items-center gap-3">
                                    <span>Sắp tới</span>
                                    <ion-icon class="show-hide-tasks" name="caret-up-outline"></ion-icon>
                                </div>
                                <div class="task-list view-list mt-2">
                                <c:forEach var="todo" items="${upcomingTodoList}">
                                    <div class="horizon-task-box d-flex align-items-center justify-content-between gap-2">
                                        <div class="td-task-content d-flex align-items-center gap-3">
                                            <div class="task-status ${todo.isCompleted ? "completed" : "not-complete"}" data-id="${!todo.isCompleted ? todo.id : ""}">
                                                <i class='bx bx-check'></i>
                                            </div>
                                            <div class="td-task-name-des d-flex flex-column">
                                                <span class="task-name text-nowrap">${todo.title}</span>
                                                <span class="task-des text-nowrap">${todo.description}</span>
                                            </div>
                                        </div>

                                        <div class="d-flex align-items-center gap-4">
                                            <div class="d-flex flex-column align-items-end">
                                                <span>${FormatLocalDateTime.format(todo.dateToComplete)}</span>
                                                <span>${todo.subTodoList.size()} Việc cần làm</span>
                                            </div>

                                            <div class="d-flex align-items-center gap-2">
                                                <div class="task-action info" data-id="${todo.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action edit" data-id="${todo.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action delete" data-id="${todo.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                                    </svg>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                </div>
                            </div>
                            
                            <div class="tasks-box today mt-3">
                                <div class="task-list-title d-flex align-items-center gap-3">
                                    <span>Hôm nay</span>
                                    <ion-icon class="show-hide-tasks" name="caret-up-outline"></ion-icon>
                                </div>
                                <div class="task-list view-list mt-2">
                                <c:forEach var="todo" items="${todoList}">
                                    <div class="horizon-task-box d-flex align-items-center justify-content-between gap-2">
                                        <div class="td-task-content d-flex align-items-center gap-3">
                                            <div class="task-status ${todo.isCompleted ? "completed" : "not-complete"}" data-id="${!todo.isCompleted ? todo.id : ""}">
                                                <i class='bx bx-check'></i>
                                            </div>
                                            <div class="td-task-name-des d-flex flex-column">
                                                <span class="task-name text-nowrap">${todo.title}</span>
                                                <span class="task-des text-nowrap">${todo.description}</span>
                                            </div>
                                        </div>

                                        <div class="d-flex align-items-center gap-4">
                                            <div class="d-flex flex-column align-items-end">
                                                <span>${FormatLocalDateTime.format(todo.dateToComplete)}</span>
                                                <span>${todo.subTodoList.size()} Việc cần làm</span>
                                            </div>

                                            <div class="d-flex align-items-center gap-2">
                                                <div class="task-action info" data-id="${todo.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action edit" data-id="${todo.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action delete" data-id="${todo.id}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                                    </svg>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </section>
                
            </main>
            
            <jsp:include page="layout/task_info.jsp" />
            <jsp:include page="layout/action_form.jsp" /> 
            <jsp:include page="layout/confirm_box.jsp" />
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
