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

$('.nav-link').click(function() {
    $('.nav-link').removeClass('active');
    $(this).addClass('active');
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


$('.task-box, .task-action.info').click(function() {
    $('.task-box').removeClass('active');
    $(this).addClass('active');
    $('.task-infomation-wrapper').removeClass('close');
    setMainWidth();
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

$('.edit-sub-task-btn').click(function() {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input').focus();
    $(this).parent().find('.edit-sub-task input').val($(this).parent().find('.sub-task-label span').text())
})

$('.edit-sub-task-submit').click(function() {
    $(this).closest('.edit-sub-task').removeClass('show');
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