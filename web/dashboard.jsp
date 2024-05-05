<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="java.time.LocalDateTime" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="utils.FormatLocalDateTime" %>
<%@page import="models.Todo" %>
<%@page import="models.Note" %>

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
                    <div class="welcome-box d-flex align-items-center justify-content-between mt-2 mb-4">
                        <div class="d-flex align-items-center">
                            <img src="images/todo-img.png" alt="">
                            <div class="ms-5">
                                <h4 class="m-0 p-0">
                                    Xin chào,&nbsp;${(user.fullName != null ? user.fullName : user.username)}
                                </h4>
                                <p class="p-0 m-0">
                                    Hãy bắt đầu với một gì đó thú vị nào!
                                </p>
                            </div>
                        </div>

                        <button class="me-4">
                            Thêm công việc
                        </button>
                    </div>

                    <div class="statistic mt-3">
                        <div class="statistic-box d-flex flex-column justify-content-between">
                            <%
//                                float allPercent = (float) request.getAttribute("AllPercent");
//                                float monthPercent = (float) request.getAttribute("MonthPercent");
//                                float weekPercent = (float) request.getAttribute("WeekPercent");
//                                float dayPercent = (float) request.getAttribute("DayPercent");
                            %>
                            <div>
                                <p class="sta-cate-name m-0">
                                    Tất cả
                                </p>
                                <p class="sta-task-count m-0">
                                    12 Việc
                                </p>
                            </div>
                            <div class="completed-percent-box mt-4">
                                <p class="m-0">
                                    <%%>%
                                </p>
                                <div class="completed-percent-bar">
                                    <div style="width: 50%;"></div>
                                </div>
                            </div>
                        </div>

                        <div class="statistic-box d-flex flex-column justify-content-between">
                            <div>
                                <p class="sta-cate-name m-0">
                                    Tháng này
                                </p>
                                <p class="sta-task-count m-0">
                                    12 Việc
                                </p>
                            </div>
                            <div class="completed-percent-box mt-4">
                                <p class="m-0">
                                    <%%>%
                                </p>
                                <div class="completed-percent-bar">
                                    <div style="width: 50%;"></div>
                                </div>
                            </div>
                        </div>

                        <div class="statistic-box d-flex flex-column justify-content-between">
                            <div>
                                <p class="sta-cate-name m-0">
                                    Tuần này
                                </p>
                                <p class="sta-task-count m-0">
                                    12 Việc
                                </p>
                            </div>
                            <div class="completed-percent-box mt-4">
                                <p class="m-0">
                                    <%%>%
                                </p>
                                <div class="completed-percent-bar">
                                    <div style="width: 50%;"></div>
                                </div>
                            </div>
                        </div>

                        <div class="statistic-box d-flex flex-column justify-content-between">
                            <div>
                                <p class="sta-cate-name m-0">
                                    Hôm nay
                                </p>
                                <p class="sta-task-count m-0">
                                    12 Việc
                                </p>
                            </div>
                            <div class="completed-percent-box mt-4">
                                <p class="m-0">
                                    <%%>%
                                </p>
                                <div class="completed-percent-bar">
                                    <div style="width: 50%;"></div>
                                </div>
                            </div>
                        </div>

                    </div>


                    <div class="d-flex gap-4 mt-4">
                        <div class="content-box weather-box d-flex flex-column justify-content-between">
                            <div class="weather d-flex flex-column gap-1 align-items-center mt-5 pe-2">
                                <div class="temperature"></div>
                                <div class="weather-description"></div>
                            </div>

                            <div class="time mb-1">
                                <div class="d-flex align-items-center justify-content-between">
                                    <div class="date"></div>
                                    <div class="clock"></div>
                                </div>
                                <div class="horizon-break"></div>
                                <div class="city"></div>  
                            </div>

                            <button class="reload-weather position-absolute top-0 end-0 mt-2 me-2">
                                <i class="fa-solid fa-rotate-right"></i>
                            </button>
                        </div>

                        <div class="content-box today-tasks">
                            <div class="header-text">
                                <span>Công việc hôm nay</span>
                            </div>
                            <div class="home-task-list d-flex flex-column gap-2 mt-2">
                                
                                <%
                                    ArrayList<Todo> todayList = (ArrayList<Todo>) request.getAttribute("TodayList");  
                                    if(todayList.size() > 0) {
                                        for(Todo todo : todayList) {
                                %>
                                    <div class="today-task-box d-flex align-items-center justify-content-between gap-2">
                                        <div class="td-task-content d-flex align-items-center gap-3">
                                            <div class="task-status <%= todo.isIsCompleted()  ? "completed" : "not-complete" %>">
                                                <i class='bx bx-check'></i>
                                            </div>
                                            <div class="td-task-name-des d-flex flex-column gap-1">
                                                <span class="task-name text-nowrap"><%= todo.getTitle() %></span>
                                                <span class="task-des text-nowrap"><%= todo.getDescription() %></span>
                                            </div>
                                        </div>

                                        <div class="d-flex align-items-center gap-4">
                                            <div class="d-flex flex-column align-items-end gap-1">
                                                <span><%= todo.getDateCompleted() != null ? FormatLocalDateTime.format(todo.getDateCompleted()) : "" %></span>
                                                <span><%= todo.getSubTodoList().size() %> Subtasks</span>
                                            </div>

                                            <div class="d-flex align-items-center gap-2">
                                                <div class="task-action info" data-id="<%= todo.getId() %>">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action edit" data-id="<%= todo.getId() %>">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                                                    </svg>
                                                </div>

                                                <div class="task-action delete" data-id="<%= todo.getId() %>">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                                        <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                                    </svg>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                <%
                                        }
                                    } else {
                                %>
                                <div class="d-flex align-items-center justify-content-center w-100 h-100">
                                    <span>Không có gì ở đây cả..</span>
                                </div>
                                <% } %>
                                
                            </div>
                        </div>
                    </div>

                    <div class="notes content-box mt-4">
                        <div class="header-text">
                            <span>Ghi chú</span>
                            <button class="add-note-btn s-btn d-flex align-items-center">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                    <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                                </svg>

                                <span>Tạo ghi chú</span>
                            </button>
                        </div>
                        <div class="note-list mt-3">
                            <% 
                                ArrayList<Note> notes = (ArrayList<Note>) request.getAttribute("Notes");
                                if(notes.size() > 0) {
                                    for(Note note : notes) {
                            %>
                            <div class="note-box">
                                <div class="note-content">
                                    <%= note.getContent() %>
                                </div>
                                <div class="note-time">
                                    <%= FormatLocalDateTime.format(note.getDateCreate()) %>
                                </div>
                            </div>
                            <%
                                    }
                                } else {
                            %>
                            
                            <%
                                }
                            %>

                        </div>
                    </div>
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
