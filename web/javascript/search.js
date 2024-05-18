$('#search').keyup(function () {
    const value = $(this).val();
    if (value.length > 0) {
        $.ajax({
            url: './api/search',
            type: 'GET',
            data: {
                q: value
            },
            success: (response) => {
                $('.search-autocomplete').empty();
                if (response.Categories.length === 0 && response.TodoList.length === 0) {
                    $('.search-autocomplete').empty();
                    $('.search-autocomplete').append(`<div class="d-flex align-items-center justify-content-center p-5 m-5">Không tìm thấy kết quả phù hợp</div>`);
                }
                else {
                    if (response.Categories.length > 0) {
                        $('.search-autocomplete').append(`<div class="mt-4"><h5>Danh mục</h5><div class="search-categories d-flex flex-wrap gap-2"></div></div>`);
                        appendSearchCategories(response.Categories);
                    }
                    if (response.TodoList.length > 0) {
                        $('.search-autocomplete').append(`<div class="mt-4"><h5>Công việc</h5><div class="search-todo d-flex flex-column gap-2"></div></div>`);
                        appendSearchTodoList(response.TodoList);
                    }
                }
            },
            error: (jqXHR, textStatus, errorThrown) => {
                $('.search-autocomplete').empty();
                $('.search-autocomplete').append(`<div class="d-flex align-items-center justify-content-center p-5 m-5">Không tìm thấy kết quả phù hợp</div>`);
            }
        });
        $('.search-autocomplete-box').addClass('show');
    }
    else {
        $('.search-autocomplete-box').removeClass('show');
    }
});
const appendSearchCategories = (data) => {
    $('.search-categories').empty();
    data.map(item => {
        const str = `<a href="./category?cid=${item.id}" class="search-cate-item d-flex align-items-center gap-1 text-decoration-none">
                        <span class="nav-color-icon" style="background: ${item.iconColor}"></span>
                        <span>${item.cateName}</span>
                    </a>`;
        $('.search-categories').append(str);
    });
};
const appendSearchTodoList = (data) => {
    $('.search-todo').empty();
    data.map(item => {
        const str = `<div class="horizon-task-box d-flex align-items-center justify-content-between gap-2">
                        <div class="td-task-content d-flex align-items-center gap-3">
                            <div class="task-status ${item.isCompleted ? "completed" : "not-complete"}" data-id="${item.id}">
                                <i class='bx bx-check'></i>
                            </div>
                            <div class="td-task-name-des d-flex flex-column">
                                <span class="task-name text-nowrap">${item.title}</span>
                                <span class="task-des text-nowrap">${item.description}</span>
                            </div>
                        </div>

                        <div class="d-flex align-items-center gap-4">
                            <div class="d-flex flex-column align-items-end">
                                <span>${item.dateToComplete}</span>
                                <span>${item.subTodoList.length} Việc cần làm</span>
                            </div>

                            <div class="d-flex align-items-center gap-2 task-action-btns">
                                <div class="task-action info" data-id="${item.id}">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                        <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
                                    </svg>
                                </div>

                                <div class="task-action edit" data-id="${item.id}">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                        <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                                    </svg>
                                </div>

                                <div class="task-action delete" data-id="${item.id}">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                        <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                    </svg>
                                </div>
                            </div>
                        </div>
                    </div>`;
        $('.search-todo').append(str);
    });
};
