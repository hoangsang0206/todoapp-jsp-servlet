declare const $: any;

function setMainWidth(): void {
    if(window.innerWidth >= 1024) {
        let main: any = $('main');
        let taskInf: any = $('.task-infomation-wrapper');
        let nav: any = $('.navigation');

        if(!taskInf.hasClass('close') && !nav.hasClass('close')) {  
            main.css('width', 'calc(100% - 570px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(3, 1fr)')

        } else if(!nav.hasClass('close') && taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 270px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, 1fr)')
        } else if(nav.hasClass('close') && !taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 370px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, 1fr)')
        } else {
            main.css('width', 'calc(100% - 70px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(5, 1fr)')
        }
    }
}

function showOverlay(): void {
    if(window.innerWidth < 1024) {
        $('.overlay').show();
    } else {
        $('.overlay').hide();
    }
}

function hideOverlay(): void {
    $('.overlay').hide();
}

function showContentLoading(content_box: any): void {
    const loader_element: string = `<div class="loader-box"><span class="loader"></span></div>`;
    content_box.css('position', 'relative');
    content_box.append(loader_element);
}

function hideContentLoading(content_box: any): void {
    setTimeout(() => {
        content_box.find('.loader-box').remove();
    }, 1000);
}

function closeTaskList(taskList: any): number {
    let height: number = taskList.height();
    let targetHeight: number = 0;
    let duration: number = 300; //0.3s

    taskList.data('box-height', height);

    taskList.animate({
        height: targetHeight
    }, duration)

    return height;
}

function showTaskList(taskList: any, height: number): void {
    let duration: number = 300; //0.3s

    taskList.animate({
        height: height
    }, duration)
}

//------------------------------------------

$(window).on('load', () => {
    $('.page-loader').removeClass('show');
})

$(window).resize(() => {
    if(window.innerWidth < 768) {
        $('.navigation').removeClass('close');
    }
})

$('.toggle-nav').click(() => {
    if(window.innerWidth < 768) {
        $('.navigation').removeClass('show');
        hideOverlay();
    } else {
        $('.navigation').toggleClass('close');
        setMainWidth();
    }
})

$('.mb-toggle-nav').click(function () { 
    $('.navigation').removeClass('show');
    hideOverlay();
});

$('.close-task-info').click(() => {
    $('.task-infomation-wrapper').addClass('close');
    setMainWidth();
    hideOverlay();
})

$('.overlay').click(() => {
    $('.navigation').removeClass('show');
    $('.task-infomation-wrapper').addClass('close');
    hideOverlay();
})

//Click to show navigation (mobile)
$('.show-nav-action').click(() => {
    $('.navigation').addClass('show');
    showOverlay();
})


$('.toggle-task-list').click(function() {
    $(this).toggleClass('active');

    let taskList: any = $(this).parent().next('.task-list');

    if(!taskList.hasClass('close')) {
        taskList.addClass('close');
        closeTaskList(taskList);
    } else {
        taskList.removeClass('close');
        
        let height: number = taskList.data('box-height');

        showTaskList(taskList, height);
    }
})

$('.add-new-task, .add-task-floating, .welcome-box button').click(() => {
    $('.create-task').addClass('show');
})

$('.cancle-create-task').click(() => {
    $('.create-task').removeClass('show');
})


$('.header-notifications').click(function() {
    $(this).find('.header-icon').toggleClass('active');
    $('.notifications-wrapper').toggleClass('show');
})

//
function setLightTheme(): void {
    $('body').css('--current-bg', 'var(--bg-light)');
    $('body').css('--current-content-bg', 'var(--bg-light-content)');
    $('body').css('--current-text-color', 'var(--text-color-black)');
    $('body').css('--nav-text', 'var(--text-color-gray)');
    
    $('.theme-toggle').css('background', '#ebebeb');
    $('.search-form').css('background', '#fff');
    $('.logo-text').css('color', 'var(--text-color-gray)');
    $('.sort-action, .filter-action, .view-action').css('background', '#e7e7e7');
}

function setDarkTheme(): void {
    $('body').css('--current-bg', 'var(--bg-dark)');
    $('body').css('--current-content-bg', 'var(--bg-dark-content)');
    $('body').css('--current-text-color', 'var(--text-color-white)');
    $('body').css('--nav-text', 'var(--text-color-white)');

    $('.theme-toggle ').css('background', '#242424');
    $('.search-form').css('background', '#3a3b3c');
    $('.search-form').css('border', 'none');
    $('.logo-text').css('color', '#fff');
    $('.sort-action, .filter-action, .view-action').css('background', 'var(--bg-dark-content)');
}

$(document).ready(function() {
    let radioThemeLight: any = $('#light-theme');
    let radioThemeDark: any = $('#dark-theme');
    let lctTheme: any = localStorage.getItem('theme');

    if(radioThemeLight.prop('checked') || lctTheme == 'light') {
        setLightTheme();

        radioThemeLight.prop('checked', true);
    } else if(radioThemeDark.prop('checked') || lctTheme == 'dark') {
        setDarkTheme();

        radioThemeDark.prop('checked', true);
    }

    radioThemeLight.change(function() {
        if($(this).prop('checked')) {
            setLightTheme();
            localStorage.setItem('theme', 'light');
        }
    })

    radioThemeDark.change(function() {
        if($(this).prop('checked')) {
            setDarkTheme();
            localStorage.setItem('theme', 'dark');
        }
    })
})


//
function appendTaskInfo(data: any) {
    $('.task-inf-name').text(data.title);
    $('.task-inf-description').text(data.description);
    $('.task-inf-cate-list').empty();
    $('.sub-task-list').empty();
              
    data.categories.forEach(category => {
        let svgStr: string = category.iconSVG;
        let taskInfItem: any = $(`<div class="task-inf-cate-item"></div>`);
        taskInfItem.append(svgStr);
        taskInfItem.append(`<span>${category.cateName}</span>`)

        $('.task-inf-cate-list').append(taskInfItem);
    })
     
    data.subTodoList.forEach(subTodo => {
        let str: string = `<div class="sub-task-item d-flex align-items-center">
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


$('.task-box, .task-action.info').click(function() {
    $('.task-box').removeClass('active');
    $(this).addClass('active');
    $('.task-infomation-wrapper').removeClass('close');
    setMainWidth();
    showOverlay();
    
    showContentLoading($('.task-infomation-wrapper'));
    
    $.ajax({
        url: '/todoapp/api/todo',
        type: 'GET',
        data: {
            user: 'admin',
            id: $(this).data('id')
        },
        success: (response) => {
            appendTaskInfo(response);
            
            hideContentLoading($('.task-infomation-wrapper'));
        },
        error: () => { }
    })
   
})


$('.completed-task-btn').click(function() {
    console.log($(this).data('id'));
});

$(document).on('click', '.edit-sub-task-btn', function() {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input').focus();
    $(this).parent().find('.edit-sub-task input').val($(this).parent().find('.sub-task-label span').text())
})

$('.edit-sub-task-submit').click(function() {
    $(this).closest('.edit-sub-task').removeClass('show');
})