<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <title>Lịch</title>
        <jsp:include page="layout/header.jsp"/>

        <link type="text/css" rel="stylesheet" href="css/calendar.css" />
        <script type="text/javascript" src="javascript/calendar.js" defer></script>
    </head>
    <body>
        <jsp:include page="layout/page_loader.jsp" />
        <section class="main-layout d-flex">
            <jsp:include page="layout/sidebar.jsp" />
            
            <main>
                <jsp:include page="layout/header_navigation.jsp" />

                <section class="main-contents">
                    <div class="calendar-wrapper">
                    <div class="calendar-header d-flex align-items-center justify-content-between">
                        <div class="c-type-select d-flex gap-2">
                            <button class="s-btn c-type-selection active">Tháng</button>
                            <button class="s-btn c-type-selection">Tuần</button>
                        </div>
                        <div class="current-day">
                            
                        </div>
                        <div class="c-next-prev d-flex align-items-center gap-1">
                            <button class="s-btn btn-today">Hôm nay</button>
                            <div class="c-vbreak"></div>
                            <button class="s-btn prev-month">
                                <i class="fa-solid fa-chevron-left"></i>
                            </button>
                            <button class="s-btn next-month">
                                <i class="fa-solid fa-chevron-right"></i>
                            </button>
                        </div>
                    </div>

                    <div class="calendar-main">
                        <div class="c-days-of-month">
                            <div>Chủ Nhật</div>
                            <div>Thứ Hai</div>
                            <div>Thứ Ba</div>
                            <div>Thứ Tư</div>
                            <div>Thứ Năm</div>
                            <div>Thứ Sáu</div>
                            <div>Thứ Bảy</div>
                        </div>

                        <div class="c-main">
                            <div class="c-days">
                                
                            </div>
                        </div>
                    </div>
                </div>
                </section>
                
            </main>
                

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
