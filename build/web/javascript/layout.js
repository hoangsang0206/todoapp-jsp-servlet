function setMainWidth() {
    if (window.innerWidth >= 1024) {
        let main = $('main');
        let taskInf = $('.task-infomation-wrapper');
        let nav = $('.sidebar');
        if (!taskInf.hasClass('close') && !nav.hasClass('close')) {
            main.css('width', 'calc(100% - 570px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(3, 1fr)');
        }
        else if (!nav.hasClass('close') && taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 270px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, 1fr)');
        }
        else if (nav.hasClass('close') && !taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 370px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, 1fr)');
        }
        else {
            main.css('width', 'calc(100% - 70px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(5, 1fr)');
        }
    }
}
function showOverlay() {
    $('.overlay').show();
}
function hideOverlay() {
    $('.overlay').hide();
}
function showContentLoading(content_box) {
    const loader_element = `<div class="loader-box"><span class="loader"></span></div>`;
    content_box.css('position', 'relative');
    content_box.append(loader_element);
}
function hideContentLoading(content_box) {
    setTimeout(() => {
        content_box.find('.loader-box').remove();
    }, 1000);
}
function closeTaskList(taskList) {
    let height = taskList.height();
    let targetHeight = 0;
    let duration = 300;
    taskList.data('box-height', height);
    taskList.animate({
        height: targetHeight
    }, duration);
    return height;
}
function showTaskList(taskList, height) {
    let duration = 300;
    taskList.animate({
        height: height
    }, duration);
}
function showActionForm(form_wrapper) {
    form_wrapper.addClass('show');
    form_wrapper.find('.form-box').addClass('show');
    $('input[type="datetime-local"]').val(() => {
        return new Date(new Date().getTime() + 60 * 60000).toLocaleString('sv', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
    });
}
function hideActionForm(form_wrapper) {
    form_wrapper.removeClass('show');
    form_wrapper.find('.form-box').removeClass('show');
    form_wrapper.find('.custom-select').removeClass('open');
}
function loadSelectData() {
    const categories = [
        {
            id: 'category1',
            cateName: 'Category 1',
            iconColor: '#e30019',
        },
        {
            id: 'category2',
            cateName: 'Category 2',
            iconColor: '#e30019',
        },
        {
            id: 'category3',
            cateName: 'Category 3',
            iconColor: '#e30019',
        },
        {
            id: 'category4',
            cateName: 'Category 4',
            iconColor: '#e30019',
        },
        {
            id: 'category5',
            cateName: 'Category 5',
            iconColor: '#e30019',
        },
        {
            id: 'category6',
            cateName: 'Category 6',
            iconColor: '#e30019',
        }
    ];
    return categories;
}
function loadSelectElements(data) {
    $('.select-options-box').empty();
    data.map(item => {
        let element = `<div class="select-option" data-value="${item === null || item === void 0 ? void 0 : item.id}" data-name="${item === null || item === void 0 ? void 0 : item.cateName}" data-color="${item === null || item === void 0 ? void 0 : item.iconColor}">
                        <span class="nav-color-icon" style="background: ${item === null || item === void 0 ? void 0 : item.iconColor};"></span>
                        <span class="select opt-name">${item === null || item === void 0 ? void 0 : item.cateName}</span>
                    </div>`;
        $('.select-options-box').append(element);
    });
}
loadSelectElements(loadSelectData());
function getSelectedElements() {
    return $('.selected-option').toArray();
}
function getSelectedValue() {
}
function checkExistItemSelected(value) {
    for (let element of getSelectedElements()) {
        if ($(element).data('value') === value) {
            return true;
        }
    }
    return false;
}
$('.show-select-options i').click(function () {
    $(this).closest('.custom-select').toggleClass('open');
});
$(document).click(function (event) {
    if (!$(event.target).closest('.custom-select').length) {
        $('.custom-select').removeClass('open');
    }
});
$(document).on('click', '.select-option', function () {
    let value = $(this).data('value');
    if (checkExistItemSelected(value)) {
        return;
    }
    let itemName = $(this).data('name');
    let colorData = $(this).data('color');
    let selectedEl = `<div class="selected-option" data-value="${value}" data-name="${itemName}" data-color="${colorData}">
                                <div class="selected-opt-content">
                                    <span class="nav-color-icon" style="background: ${colorData};"></span>
                                    <span class="selected-opt-name">${itemName}</span>
                                </div>
                                <div class="delete-selected-opt">
                                    <i class='bx bx-x'></i>
                                </div>
                            </div>`;
    $('.selected-box').append(selectedEl);
});
$(document).on('click', '.delete-selected-opt', function () {
    let parentEl = $(this).closest('.selected-option');
    parentEl.remove();
});
$('#search-option').keyup(function () {
    const searchText = $(this).val();
    const categories = loadSelectData();
    if (searchText.length > 0) {
        let filteredCategories = categories.filter(category => category === null || category === void 0 ? void 0 : category.cateName.toLowerCase().includes(searchText.toLowerCase()));
        loadSelectElements(filteredCategories);
    }
    else {
        loadSelectElements(categories);
    }
});
$(window).on('load', () => {
    $('.page-loader').removeClass('show');
});
$(window).resize(() => {
    if (window.innerWidth < 768) {
        $('.sidebar').removeClass('close');
    }
});
$('.toggle-nav').click(() => {
    if (window.innerWidth < 768) {
        $('.sidebar').removeClass('show');
        hideOverlay();
    }
    else {
        $('.sidebar').toggleClass('close');
        setMainWidth();
    }
});
$('.mb-toggle-nav').click(function () {
    $('.sidebar').removeClass('show');
    hideOverlay();
});
$('.close-task-info').click(() => {
    $('.task-infomation-wrapper').addClass('close');
    setMainWidth();
    hideOverlay();
});
$('.overlay').click(() => {
    $('.sidebar').removeClass('show');
    $('.task-infomation-wrapper').addClass('close');
    hideActionForm($('.form-full-wrapper'));
    hideOverlay();
});
$('.show-nav-action').click(() => {
    $('.sidebar').addClass('show');
    showOverlay();
});
$('.toggle-task-list').click(function () {
    $(this).toggleClass('active');
    let taskList = $(this).parent().next('.task-list');
    if (!taskList.hasClass('close')) {
        taskList.addClass('close');
        closeTaskList(taskList);
    }
    else {
        taskList.removeClass('close');
        let height = taskList.data('box-height');
        showTaskList(taskList, height);
    }
});
$('.edit-sub-task-btn').click(function () {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input').focus();
    $(this).parent().find('.edit-sub-task input').val($(this).parent().find('.sub-task-label span').text());
});
$('.edit-sub-task-submit').click(function () {
    $(this).closest('.edit-sub-task').removeClass('show');
});
$('.add-new-task, .add-task-floating, .welcome-box button').click(() => {
    showActionForm($('.create-task-wrapper'));
    showOverlay();
});
$('.close-form-btn').click(function () {
    hideActionForm($(this).closest('.form-container'));
    hideOverlay();
});
$('.add-cate-header, .add-cate-action').click(() => {
    showActionForm($('.create-category-wrapper'));
    showOverlay();
});
$('.add-note-btn').click(() => {
    showOverlay();
});
$('.header-notifications').click(function () {
    $(this).find('.header-icon').toggleClass('active');
    $('.notifications-wrapper').toggleClass('show');
});
function setLightTheme() {
    $('body').css('--current-bg', 'var(--bg-light)');
    $('body').css('--current-content-bg', 'var(--bg-light-content)');
    $('body').css('--current-text-color', 'var(--text-color-black)');
    $('body').css('--current-task-background', 'var(--task-background-light)');
    $('body').css('--nav-text', 'var(--text-color-gray)');
    $('body').css('--border-input', '#ddd');
    $('.theme-toggle').css('background', '#ebebeb');
    $('.search-form').css('background', '#fff');
    $('.logo-text').css('color', 'var(--text-color-gray)');
    $('.sort-action, .filter-action, .view-action').css('background', '#e7e7e7');
}
function setDarkTheme() {
    $('body').css('--current-bg', 'var(--bg-dark)');
    $('body').css('--current-content-bg', 'var(--bg-dark-content)');
    $('body').css('--current-text-color', 'var(--text-color-white)');
    $('body').css('--current-task-background', 'var(--task-background-dark)');
    $('body').css('--nav-text', 'var(--text-color-white)');
    $('body').css('--border-input', 'var(--border-input-dark)');
    $('.theme-toggle ').css('background', '#242424');
    $('.search-form').css('background', '#3a3b3c');
    $('.search-form').css('border', 'none');
    $('.logo-text').css('color', '#fff');
    $('.sort-action, .filter-action, .view-action').css('background', 'var(--bg-dark-content)');
}
$(document).ready(function () {
    let radioThemeLight = $('#light-theme');
    let radioThemeDark = $('#dark-theme');
    let lctTheme = localStorage.getItem('theme');
    if (radioThemeLight.prop('checked') || lctTheme == 'light') {
        setLightTheme();
        radioThemeLight.prop('checked', true);
    }
    else if (radioThemeDark.prop('checked') || lctTheme == 'dark') {
        setDarkTheme();
        radioThemeDark.prop('checked', true);
    }
    radioThemeLight.change(function () {
        if ($(this).prop('checked')) {
            setLightTheme();
            localStorage.setItem('theme', 'light');
        }
    });
    radioThemeDark.change(function () {
        if ($(this).prop('checked')) {
            setDarkTheme();
            localStorage.setItem('theme', 'dark');
        }
    });
});
$('.sort-filter-box button').click(function () {
    $('.view-action-box').not($(this).next('.view-action-box')).removeClass('show');
    $(this).next('.view-action-box').toggleClass('show');
});
$('.form-container').click(function (event) {
    if (!$(event.target).closest('.form-box').length) {
        hideActionForm($(this));
        hideOverlay();
    }
});
function appendTaskInfo(data) {
    $('.task-inf-name').text(data.title);
    $('.task-inf-description').text(data.description);
    $('.task-inf-cate-list').empty();
    $('.sub-task-list').empty();
    data.categories.forEach(category => {
        let svgStr = category.iconSVG;
        let taskInfItem = $(`<div class="task-inf-cate-item"></div>`);
        taskInfItem.append(svgStr);
        taskInfItem.append(`<span>${category.cateName}</span>`);
        $('.task-inf-cate-list').append(taskInfItem);
    });
    data.subTodoList.forEach(subTodo => {
        let str = `<div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-1" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-1">
                            <span>${subTodo.title}</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="#">
                                <input type="text" name="subtodo_title" required autocomplete="off">
                                <input type="hidden" name="subtdo_id" value="${subTodo.id}" required />
                                <button type="submit" class="edit-sub-task-submit">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m10 15.586-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z"></path>
                                    </svg>
                                </button>
                            </form>
                            <button class="delete-sub-task" data-id="${subTodo.id}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                    <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                </svg>
                            </button>
                        </div>
                    </div>`;
        $('.sub-task-list').append(str);
    });
    $('.completed-task-btn').data('id', data.id);
    $('.edit-task-btn').data('id', data.id);
    $('.delete-task-btn').data('id', data.id);
}
$('.task-box, .task-action.info').click(function () {
    $('.task-box').removeClass('active');
    $(this).addClass('active');
    $('.task-infomation-wrapper').removeClass('close');
    setMainWidth();
    if (window.innerWidth < 768) {
        showOverlay();
    }
    showContentLoading($('.task-infomation-wrapper'));
    $.ajax({
        url: '/todoapp/api/todo',
        type: 'GET',
        data: {
            user: 'sang',
            id: $(this).data('id')
        },
        success: (response) => {
            appendTaskInfo(response);
            hideContentLoading($('.task-infomation-wrapper'));
        },
        error: () => { }
    });
});
$('.completed-task-btn').click(function () {
    console.log($(this).data('id'));
});
$(document).on('click', '.edit-sub-task-btn', function () {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input').focus();
    $(this).parent().find('.edit-sub-task input').val($(this).parent().find('.sub-task-label span').text());
});
$('.edit-sub-task-submit').click(function () {
    $(this).closest('.edit-sub-task').removeClass('show');
});
