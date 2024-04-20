<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="task-infomation-wrapper d-flex flex-column justify-content-between position-absolute close">
    <div>
        <div class="task-inf-header d-flex justify-content-between align-items-center">
            <h4>Chi tiết</h4>
            <svg class="close-task-info" xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24">
                <path d="m16.192 6.344-4.243 4.242-4.242-4.242-1.414 1.414L10.535 12l-4.242 4.242 1.414 1.414 4.242-4.242 4.243 4.242 1.414-1.414L13.364 12l4.242-4.242z"></path>
            </svg>
        </div>
        <div class="task-inf-box">
            <div class="task-inf">
                <div class="task-inf-name mb-1">Tên công việc</div>
                <div class="task-inf-description">
                    Mô tả công việc
                </div>
                <hr />
                <div class="task-inf-categories">
                    <div class="task-inf-cate-name mb-1">Danh mục</div>
                    <div class="task-inf-cate-list d-flex align-items-center flex-wrap column-gap-4">
                        <div class="task-inf-cate-item">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                <path d="M2 7v1l11 4 9-4V7L11 4z"></path>
                                <path d="M4 11v4.267c0 1.621 4.001 3.893 9 3.734 4-.126 6.586-1.972 7-3.467.024-.089.037-.178.037-.268V11L13 14l-5-1.667v3.213l-1-.364V12l-3-1z"></path>
                            </svg>
                            <span>Học tập</span>
                        </div>
                        <div class="task-inf-cate-item">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                <path d="M2 7v1l11 4 9-4V7L11 4z"></path>
                                <path d="M4 11v4.267c0 1.621 4.001 3.893 9 3.734 4-.126 6.586-1.972 7-3.467.024-.089.037-.178.037-.268V11L13 14l-5-1.667v3.213l-1-.364V12l-3-1z"></path>
                            </svg>
                            <span>Tào lao</span>
                        </div>
                        <div class="task-inf-cate-item">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                <path d="M2 7v1l11 4 9-4V7L11 4z"></path>
                                <path d="M4 11v4.267c0 1.621 4.001 3.893 9 3.734 4-.126 6.586-1.972 7-3.467.024-.089.037-.178.037-.268V11L13 14l-5-1.667v3.213l-1-.364V12l-3-1z"></path>
                            </svg>
                            <span>Tào lao</span>
                        </div>                            
                    </div>
                </div>
            </div>

            <div class="sub-task mt-5">
                <div class="sub-task-header d-flex align-items-center justify-content-between mb-3">
                    <h5 class="p-0 m-0">Việc cần làm</h5>
                    <svg class="add-sub-task" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                        <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                    </svg>
                </div>
                <div class="sub-task-list d-flex flex-column gap-2">
                    <div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-1" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-1">
                            <span>Việc 1</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="#">
                                <input type="text" required autocomplete="off">
                                <button type="submit" class="edit-sub-task-submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m10 15.586-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z"></path>
                                    </svg>
                                </button>
                            </form>
                            <button class="delete-sub-task">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                    <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-2" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-2">
                            <span>Việc 2</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="#">
                                <input type="text" required autocomplete="off">
                                <button type="submit" class="edit-sub-task-submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m10 15.586-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z"></path>
                                    </svg>
                                </button>
                            </form>
                            <button class="delete-sub-task">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                    <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                </svg>
                            </button>
                        </div>
                    </div>
                    <div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-3" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-3">
                            <span>Việc 3</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="#">
                                <input type="text" required autocomplete="off">
                                <button type="submit" class="edit-sub-task-submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m10 15.586-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z"></path>
                                    </svg>
                                </button>
                            </form>
                            <button class="delete-sub-task">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                    <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="completed-delete-task d-flex align-items-center justify-content-between gap-2">
        <button class="completed-task-btn s-btn">Hoàn thành</button>
        <button class="edit-task-btn s-btn">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
            </svg>
        </button>
        <button class="delete-task-btn s-btn">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path>
                <path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
            </svg>
        </button>
    </div>
</section>