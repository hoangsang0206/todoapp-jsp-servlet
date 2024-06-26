declare const $: any;

$(window).on('load', () => {
    setTimeout(() => {
        $('.page-loader').removeClass('show')
    }, 500);
})

$('.register-form, .login-form').submit(function() {
    showButtonLoader($('.ac-button'));
})

const setMainWidth = (): void => {
    if(window.innerWidth >= 1024) {
        let main: any = $('main');
        let taskInf: any = $('.task-infomation-wrapper');
        let nav: any = $('.sidebar');

        if(!taskInf.hasClass('close') && !nav.hasClass('close')) {  
            main.css('width', 'calc(100% - 570px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(3, minmax(0, 1fr))')

        } else if(!nav.hasClass('close') && taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 270px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, minmax(0, 1fr))')
        } else if(nav.hasClass('close') && !taskInf.hasClass('close')) {
            main.css('width', 'calc(100% - 370px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(4, minmax(0, 1fr))')
        } else {
            main.css('width', 'calc(100% - 70px)');
            main.find('.task-list').css('grid-template-columns', 'repeat(5, minmax(0, 1fr))')
        }
    }
}

const showOverlay = (): void => {
    $('.overlay').show();
}

const hideOverlay = (): void => {
    $('.overlay').hide();
}

const showContentLoading = (content_box: any): void => {
    if(content_box.find('.loader-box').length <= 0) {
        const loader_element: string = `<div class="loader-box">
        <svg class="loader-container" viewBox="0 0 40 40" height="40" width="40">
            <circle class="track" cx="20" cy="20" r="17.5" pathlength="100" stroke-width="3px" fill="none" />
            <circle class="car" cx="20" cy="20" r="17.5" pathlength="100" stroke-width="3px" fill="none" />
        </svg></span></div>`;
        
        content_box.css('position', 'relative');
        content_box.append(loader_element);
    } 
}

const hideContentLoading = (content_box: any): void => {
    setTimeout(() => {
        content_box.find('.loader-box').remove();
    }, 1000);
}

const showActionForm = (form_wrapper: any): void => {
    form_wrapper.addClass('show');
    form_wrapper.find('.form-box').addClass('show');
    if(window.innerWidth < 1024) {
        $('.sidebar').removeClass('show');
    }

    $('input[type="datetime-local"]').val(() => {
        return new Date(new Date().getTime() + 60 * 60000).toLocaleString('sv', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
    });
}

const hideActionForm = (form_wrapper: any): void => {
    form_wrapper.removeClass('show');
    form_wrapper.find('.form-box').removeClass('show');
    form_wrapper.find('.custom-select').removeClass('open');
}

const clearFormValue = (form: any) => {
    const input_elements = form.find('input').not('[type="datetime-local"], [type="radio"]').toArray();
    const selectedOptions = form.find('.selected-option').toArray();
    
    input_elements.map((input) => {
        $(input).val('');
    })
    
    selectedOptions.map((item) => {
        $(item).remove();
    })
}

const showConfirmBox = (message: string, sub_message: string, data_action: string, color: string) => {
    $('.confirm-wrapper').addClass('show');
    $('.confirm-box').addClass('show');
    $('.confirm-message').html(message);
    $('.confirm-sub-message').html(sub_message)
    $('.cancel-btn').data('confirm', data_action);
    $('.confirm-btn').data('confirm', data_action);
    if(color != null && color.length > 0) {
        $('.confirm-btn').css('background-color', color);
    }
}

const hideConfirmBox = () => {
    $('.confirm-wrapper').removeClass('show');
    $('.confirm-box').removeClass('show');
    $('.confirm-message').empty();
    $('.confirm-sub-message').empty();
    $('.cancel-btn').removeAttr('data-confirm');
    $('.confirm-btn').removeAttr('data-confirm');
}

const showOkBox = (message: string) => {
    $('.success-wrapper').addClass('show');
    $('.success-box').addClass('show');
    $('.success-message').html(message);
}

const hideOkBox = () => {
    $('.success-wrapper').removeClass('show');
    $('.success-box').removeClass('show');
    $('.success-message').empty();
}

const showButtonLoader = (button: any): string => {
    const loader_element: string = `<div class="loader-box">
        <svg class="button-loader" viewBox="0 0 40 40" height="20" width="20">
            <circle class="track" cx="20" cy="20" r="17.5" pathlength="100" stroke-width="3px" fill="none" />
            <circle class="car" cx="20" cy="20" r="17.5" pathlength="100" stroke-width="3px" fill="none" />
        </svg></span></div>`;
    
    const [btnWidth, btnHeight] = [button.outerWidth(), button.outerHeight()];
    const btnElement: string = button.html();
    
    button.css('width', btnWidth);
    button.css('height', btnHeight);
    button.css('position', 'relative');
    button.html(loader_element);
    
    return btnElement;
}

const hideButtonLoader = (button: any, html: string) => {
    button.html(html);
}

$('.cancel-btn').click(() => {
    hideConfirmBox();
    hideOverlay();
})

$('.success-btn').click(() => {
    hideOkBox();
    hideOverlay();
})

//Custom select ------

const loadSelectData = (): Promise<any[]> => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: './api/categories',
            type: 'GET',
            success: (response) => {    
                if(response != null) {
                    resolve(response);
                } else {
                    reject(new Error("Empty response"));
                }
            },
            error: (error) => {
                console.error(error);
                reject(error);
            }
        })
    })
}

const loadSelectElements = (data: any[], form_box: any): void => {
    form_box.find('.select-options-box').empty();
    data.map(item => {
        let element = `<div class="select-option" data-value="${item?.id}" data-name="${item?.cateName}" data-color="${item?.iconColor}">
                        <span class="nav-color-icon" style="background: ${item?.iconColor != null ? item.iconColor : "#e30019"};"></span>
                        <span class="select opt-name">${item?.cateName}</span>
                    </div>`;

        form_box.find('.select-options-box').append(element);
    })  
}

const getSelectedElements = (form_box: any) => {
    return form_box.find('.selected-option').toArray();
}

const getSelectedValue = (form_box: any): string[] => {
    const selectedElements = getSelectedElements(form_box);
    let selectedValues: string[] = [];
    selectedElements.map((item) => {
        selectedValues.push($(item).data('value'))
    })
    
    return selectedValues;
}

const checkExistItemSelected = (value: string, form_box: any): boolean => {
    for(let element of getSelectedElements(form_box)) {
        if($(element).data('value') === value) {
            return true;
        }
    }

    return false;
}

$('.show-select-options i').click(function() {
    $(this).closest('.custom-select').toggleClass('open');
})

$(document).click(function(event) {
    if(!$(event.target).closest('.custom-select').length) {
        $('.custom-select').removeClass('open');
    }
})

const pushSelectItem = (form_box: any, value: string, name: string, color: string) => {
    let selectedEl: string = `<div class="selected-option" data-value="${value}" data-name="${name}" data-color="${color}">
                                <div class="selected-opt-content">
                                    <span class="nav-color-icon" style="background: ${color != 'undefined' && color.length > 0 ? color : "#e30019"};"></span>
                                    <span class="selected-opt-name">${name}</span>
                                </div>
                                <div class="delete-selected-opt">
                                    <i class='bx bx-x'></i>
                                </div>
                            </div>`;

    form_box.find('.selected-box').append(selectedEl);
}

$(document).on('click', '.select-option', function() {
    let value: string = $(this).data('value');

    if(checkExistItemSelected(value, $(this).closest('.form-box'))) {
        return;
    }

    let itemName: string = $(this).data('name');
    let colorData: string = $(this).data('color');
    
    pushSelectItem($(this).closest('.form-box'), value, itemName, colorData);
})

$(document).on('click', '.delete-selected-opt', function() {
    let parentEl: any = $(this).closest('.selected-option');
    parentEl.remove();
})

$('input[name="search-option"]').keyup(function() {
    const searchText = $(this).val();
 
    loadSelectData()
        .then((data) => {
            if(searchText.length > 0) {
                let filteredCategories = data.filter(category => category?.cateName.toLowerCase().includes(searchText.toLowerCase()));
                loadSelectElements(filteredCategories, $(this).closest('.form-box'));
            } else {
                loadSelectElements(data, $(this).closest('.form-box'));
            }
        })
        .catch((eror) => {
            
        })
})


//------------------------------------------
$('.btn-show-search').click(() => {
    $('.search-form').addClass('show');
})

$('.btn-close-search').click(() => {
    $('.search-form').removeClass('show');
})


$(window).resize(() => {
    if(window.innerWidth < 768) {
        $('.sidebar').removeClass('show');
    }
})

$('.toggle-nav').click(() => {
    if(window.innerWidth < 768) {
        $('.sidebar').removeClass('show');
        hideOverlay();
    } else {
        $('.sidebar').toggleClass('close');
        setMainWidth();
    }
})

$('.mb-toggle-nav').click(function () { 
    $('.sidebar').removeClass('show');
    hideOverlay();
});

$('.close-task-info').click(() => {
    $('.task-infomation-wrapper').addClass('close');
    setMainWidth();
    hideOverlay();
})

$('.overlay').click(() => {
    $('.sidebar').removeClass('show');
    
    $('.form-container').toArray().map((item) => {
        hideActionForm($(item));
    })
    
    if(window.innerWidth < 1024) {
        $('.task-infomation-wrapper').addClass('close');   
    }
    
    hideOverlay();
    hideConfirmBox();
})

//Click to show navigation (mobile)
$('.show-nav-action').click(() => {
    $('.sidebar').addClass('show');
    showOverlay();
})


$('.show-hide-tasks').click(function() {
    $(this).closest('.tasks-box').toggleClass('hide');
    $(this).toggleClass('rotate');
})

$(document).on('click', '.edit-sub-task-btn', function() {
    $(this).parent().find('.edit-sub-task').addClass('show');
    $(this).parent().find('.edit-sub-task input[name="subtodo_title"]').focus();
})

$('.edit-sub-task-submit').click(function() {
    $(this).closest('.edit-sub-task').removeClass('show');
})

$('.add-new-task, .add-task-floating, .welcome-box button').click(() => {
    showActionForm($('.create-task-wrapper'));
    showOverlay();
    
    loadSelectData()
        .then((data) => {
            loadSelectElements(data, $('.create-task'))
        })
        .catch((error) => { })
})

$('.close-form-btn').click(function() {
    hideActionForm($(this).closest('.form-container'));
    hideOverlay();
})

$(document).on('click', '.add-cate-header, .add-cate-action', () => {
    showActionForm($('.create-category-wrapper'));
    showOverlay();
})

$('.add-note-btn').click(() => {
    
    showOverlay();
})


$('.header-notifications').click(function() {
    $(this).find('.header-icon').toggleClass('active');
    $('.notifications-wrapper').toggleClass('show');
})

//
const setLightTheme = (): void => {
    $('body').css('--loader-color', 'var(--loader-color-dark)');
    $('body').css('--current-bg', 'var(--bg-light)');
    $('body').css('--current-content-bg', 'var(--bg-light-content)');
    $('body').css('--current-text-color', 'var(--text-color-black)');
    $('body').css('--current-task-background', 'var(--task-background-light)');
    $('body').css('--nav-text', 'var(--text-color-gray)');
    $('body').css('--border-input', '#ddd');
    
    $('.theme-toggle').css('background', '#ebebeb');
    $('#search').css('background', '#fff');
    $('.logo-text').css('color', 'var(--text-color-gray)');
    $('.sort-action, .filter-action, .view-action').css('background', '#e7e7e7');
}

const setDarkTheme = (): void => {
    $('body').css('--loader-color', 'var(--loader-color-light)');
    $('body').css('--current-bg', 'var(--bg-dark)');
    $('body').css('--current-content-bg', 'var(--bg-dark-content)');
    $('body').css('--current-text-color', 'var(--text-color-white)');
    $('body').css('--current-task-background', 'var(--task-background-dark)');
    $('body').css('--nav-text', 'var(--text-color-white)');
    $('body').css('--border-input', 'var(--border-input-dark)');

    $('.theme-toggle ').css('background', '#242424');
    $('#search').css('background', '#3a3b3c');
    $('#search').css('border', 'none');
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

$('.sort-filter-box button').click(function() {
    $('.view-action-box').not($(this).next('.view-action-box')).removeClass('show');
    $(this).next('.view-action-box').toggleClass('show');
})

$('.form-container').click(function(event) {
    if(!$(event.target).closest('.form-box').length) {
        hideActionForm($('.form-container'));
        hideOverlay();
    }
})

$('.confirm-wrapper').click(function(event) {
    if(!$(event.target).closest('.confirm-box').length) {
        hideConfirmBox();
        hideOverlay();
    }
})

const getURL = (): URL => {
    return new URL(window.location.href);
}

const getParams = (): URLSearchParams => {
    const url: URL = getURL();
    const params = url.searchParams
    
    return params;
}

$('input[name="view-type"]').on('change', function() {
    if($(this).prop('checked')) {
        const value: string = $(this).val();
        const url: URL = getURL();
        const params: URLSearchParams = getParams();
        
        if(params.has('view')) {
            params.set('view', value);
        } else {
            params.append('view', value);
        }
        
        url.search = params.toString();
        window.location.href = url.href;
    }
    
})

$('input[name="sort-by"]').on('change', function() {
    if($(this).prop('checked')) {
        const value: string = $(this).val();
        const url: URL = getURL();
        const params: URLSearchParams = getParams();
        
        if(params.has('sort')) {
            params.set('sort', value);
        } else {
            params.append('sort', value);
        }
        
        url.search = params.toString();
        window.location.href = url.href;
    }
})



//
const appendTaskInfo = (data: any) => {
    $('.task-inf-name').text(data.title);
    $('.task-inf-description').text(data.description);
    $('.task-inf-time').text(data.dateToComplete);
    $('.task-inf-cate-list').empty();
    $('.sub-task-list').empty();
    $('.edit-task-btn').data('id', data.id);
    $('.delete-task-btn').data('id', data.id); 
    $('.add-sub-task').data('id', data.id);
    
    if(!data.isCompleted) {
        $('.completed-task-btn').data('id', data.id);
        $('.completed-task-btn').prop('disabled', false);
    } else {
        $('.completed-task-btn').removeAttr('id');
        $('.completed-task-btn').prop('disabled', true);
    }
              
    data.categories.forEach(category => {
        let iconColor: string = category.iconColor;
        let taskInfItem: any = $(`<div class="task-inf-cate-item d-flex align-items-center"></div>`);
        taskInfItem.append(`<span class="nav-color-icon" style="background: ${typeof iconColor !== 'undefined' ? iconColor : "#e30019"};"></span>`);
        taskInfItem.append(`<span>${category.cateName}</span>`)

        $('.task-inf-cate-list').append(taskInfItem);
    })
    
    let i = 0;
    data.subTodoList.forEach(subTodo => {
        i++;
        let str: string = `<div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-${i}" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-${i}" value="${subTodo.id}" name="sub-task-cbox" ${subTodo.isCompleted ? 'checked' : ''}>
                            <span>${subTodo.title}</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="POST">
                                <input type="text" name="subtodo_title" value=${subTodo.title} required autocomplete="off">
                                <input type="hidden" name="subtodo_id" value="${subTodo.id}" required />
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
}


$(document).on('click', '.task-box, .task-action.info', function() {
    $('.task-box').removeClass('active');
    $(this).addClass('active');
    $('.task-infomation-wrapper').removeClass('close');
    setMainWidth();
    
    showContentLoading($('.task-infomation-wrapper'));
    let id = $(this).data('id');

    if(window.innerWidth < 768) {
        showOverlay();
    }
    
    $.ajax({
        url: './api/todo',
        type: 'GET',
        data: {
            id: id
        },
        success: (response) => {
            appendTaskInfo(response);
            
            hideContentLoading($('.task-infomation-wrapper'));
        },
        error: () => { }
    })
   
})

$(document).on('click', '.horizon-task-box, .today-task-box', function(e) {
    if(window.innerWidth < 768) {
        if($(e.target).closest('.task-status').length > 0) {
            return;
        }
        
        $('.task-infomation-wrapper').removeClass('close');
        showContentLoading($('.task-infomation-wrapper'));
        showOverlay();
        const id = $(this).find($('.task-status ')).data('id');
        
        $.ajax({
            url: './api/todo',
            type: 'GET',
            data: {
                id: id
            },
            success: (response) => {
                appendTaskInfo(response);
                hideContentLoading($('.task-infomation-wrapper'));
            },
            error: () => { }
        })
    }
})

$('.edit-sub-task-submit').click(function() {
    $(this).closest('.edit-sub-task').removeClass('show');
})


// -----------------------------------------------
//Todo CRUD -------------
//Create todo
$(document).ready(() => {
    $('.create-task form').submit(function(e) {
        e.preventDefault();
        
        const name: string = $(e.target).find('#task-name').val();
        const description: string = $(e.target).find('#task-description').val();
        const date: string = $(e.target).find('#task-time').val();
        const categories = getSelectedValue($(this).closest('.form-box'));
        
        const submitBtn = $(e.target).find('.submit-form-btn');
        const btn_element: string = showButtonLoader(submitBtn);
        const params: URLSearchParams = getParams();
        
        $.ajax({
            url: './api/todo',
            type: 'POST',
            data: {
               title: name,
               description: description,
               date: date,
               categories: categories.join(',')
            },
            success: (response) => {  
                setTimeout(() => {
                    clearFormValue($(e.target))
                    hideButtonLoader(submitBtn, btn_element)
                    getTodayTodoList();
                    getUpcomingTodoList();
                    getFilterdTodoList();
                    getCategoryTodoList(params.has('cid') ? params.get('cid') : "");
                    
                }, 1000)
            },
            error: (error) => {
                console.error('Error when create todo.')
            }
        })
    })
})


//GET today todo list
const appendTodayTodoList = (data) => {
    if(data.length > 0) {
        $('.home-task-list').empty();
        data.map((item) => {
            let str: string = `<div class="today-task-box d-flex align-items-center justify-content-between gap-2">
                            <div class="td-task-content d-flex align-items-center gap-3">
                                <div class="task-status ${item?.isCompleted ? 'completed' : 'not-complete'}" data-id="${item?.id}">
                                    <i class='bx bx-check'></i>
                                </div>
                                <div class="td-task-name-des d-flex flex-column gap-1">
                                    <span class="task-name text-nowrap">${item?.title}</span>
                                    <span class="task-des text-nowrap">${item?.description}</span>
                                </div>
                            </div>

                            <div class="d-flex align-items-center gap-4">
                                <div class="d-flex flex-column align-items-end gap-1">
                                    <span>${item.dateToComplete != 'undefined' ? item.dateToComplete : ""}</span>
                                    <span>${item?.subTodoList.length} Việc cần làm</span>
                                </div>

                                <div class="d-flex align-items-center gap-2 task-action-btns">
                                    <div class="task-action info" data-id="${item?.id}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                            <path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M11 11h2v6h-2zm0-4h2v2h-2z"></path>
                                        </svg>
                                    </div>

                                    <div class="task-action edit" data-id="${item?.id}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                            <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                                        </svg>
                                    </div>

                                    <div class="task-action delete" data-id="${item?.id}">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24">
                                            <path d="M5 20a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8h2V6h-4V4a2 2 0 0 0-2-2H9a2 2 0 0 0-2 2v2H3v2h2zM9 4h6v2H9zM8 8h9v12H7V8z"></path><path d="M9 10h2v8H9zm4 0h2v8h-2z"></path>
                                        </svg>
                                    </div>
                                </div>
                            </div>
                        </div>`;

            $('.home-task-list').append(str);
        });
    } else {
        $('.home-task-list').html(`<div class="d-flex align-items-center justify-content-center w-100 h-100">
                            <span>Không có gì ở đây cả..</span>
                        </div>`);
    }
}

const appendTodoList = (tasks_box: any, data: any) => {
    tasks_box.find('.task-list').empty();
    
    if(data.length > 0) {
        const params: URLSearchParams = getParams();
    
        data.map((item) => {
            let str;

            if(params.has('view') && params.get('view') === 'grid') {
                str = `<div class="task-box d-flex flex-column justify-content-between" data-id="${item.id}">
                        <div class="task-box-header d-flex align-items-start justify-content-between">
                            <div class="task-box-categories d-flex align-items-center gap-3 me-2">
                                ${item.categories.map(cate =>
                                     `<div class="task-box-category d-flex align-items-center gap-2">
                                        <span class="nav-color-icon" style="background: ${cate.iconColor};"></span>
                                        <span>${cate.cateName}</span>
                                    </div>`
                                ).join('')}
                            </div>
                            <div class="show-task-inf">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                                    <path d="M12 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zm6 0c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2zM6 10c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"></path>
                                </svg>
                            </div>
                        </div>
                        <div class="task-box-main">
                            <div class="task-box-name">
                                ${item.title}
                            </div>
                            <div class="task-box-des">
                                ${item.description}
                            </div>
                        </div>
                        <div class="task-box-time d-flex align-items-center justify-content-between gap-3">
                            <span>${item.dateToComplete != 'undefined' ? item.dateToComplete.split(' ')[1] : ""}</span>
                            <span>${item.dateToComplete != 'undefined' ? item.dateToComplete.split(' ')[0] : ""}</span>
                        </div>
                    </div>`;
                    
                   
            } else {
                str = `<div class="horizon-task-box d-flex align-items-center justify-content-between gap-2">
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
            }
            
            tasks_box.find('.task-list').append(str);
        });
    }
}

const appendAllTaskPageTodoList = (data: any, taskListBox: any) => {
    if(taskListBox.length > 0) {    
        appendTodoList(taskListBox.find('.tasks-box.upcoming'), data.upcoming);
        appendTodoList(taskListBox.find('.tasks-box.today'), data.today);
        appendTodoList(taskListBox.find('.tasks-box.week'), data.week);
        appendTodoList(taskListBox.find('.tasks-box.before'), data.beforeWeek);
    }
}


const getTodayTodoList = () => {
    showContentLoading($('.home-task-list'));
    showContentLoading($('.main-contents.today-page'));
    
    $.ajax({
        url: './api/todo?t=day',
        type: 'GET',
        success: (response) => {
            const homeTask = $('.home-task-list');
            const taskBox = $('.main-contents.today-page .tasks-box.today');
            if(homeTask.length > 0) {
                appendTodayTodoList(response);
                hideContentLoading(homeTask);
            } else if(taskBox.length > 0) {
                appendTodoList(taskBox, response);   
                hideContentLoading($('.main-contents.today-page, .main-contents.all-page'));
            }
                        
        },
        eror: (error) => {
            console.error("Cannot get todo list.")
        }
    })
}

const getUpcomingTodoList = () => {
    const todayPage = $('.main-contents.today-page');
    
    if(todayPage.length > 0) {
        showContentLoading(todayPage);

        $.ajax({
            url: './api/todo?t=upcoming',
            type: 'GET',
            success: (response) => {
                const taskBox = todayPage.find(' .tasks-box.upcoming');
                appendTodoList(taskBox, response);   
                hideContentLoading($('.main-contents.today-page'));       
            },
            eror: (error) => {
                console.error("Cannot get todo list.")
            }
        });
    }
}

const getFilterdTodoList = (): any => {
    if($('.main-contents.all-page').length <= 0) {
        return;
    }
    
    $.ajax({
        url: './api/todo?t=filter',
        type: 'GET',
        success: (response) => {
            appendAllTaskPageTodoList(response, $('.main-contents.all-page'));
        },
        error: (error) => {
        }
    })
}

const getCategoryTodoList = (id: string): any => {
    if($('.main-contents.todocate-page').length <= 0) {
        return;
    }

    $.ajax({
        url: `./api/todo?t=category&cid=${id}`,
        type: 'GET',
        success: (response) => {
            appendAllTaskPageTodoList(response, $('.main-contents.todocate-page'));
        },
        error: (error) => {
        }
    })
}

//GET todo
const getTodo = (id: string): Promise<any> => {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `./api/todo?id=${id}`,
            type: 'GET',
            success: (response) => {
                resolve(response);
            },
            error: (error) => {
                console.error(`Cannot get Todo ${id}`);
                
                reject(error);
            }
        })
    })
}

//Update todo
$(document).on('click', '.task-action.edit, .edit-task-btn', function() {
    showActionForm($('.edit-task-wrapper'));
    showOverlay();
    $('.task-infomation-wrapper').addClass('close');
    setMainWidth();
    
    clearFormValue($('.edit-task form'));
    
    loadSelectData()
        .then((data) => {
            loadSelectElements(data, $('.edit-task'))
        })
        .catch((error) => { })
    
    const id = $(this).data('id');
    getTodo(id).then((data) => {
        $('#edit-task-id').val(data.id);
        $('#edit-task-name').val(data.title);
        $('#edit-task-description').val(data.description);
        if(data.isCompleted) {
            $('#todo_completed').prop('checked', true);
        } else {
            $('#todo_notcomplete').prop('checked', true);
        }
        
        const date: string = data?.dateToComplete;
        if(typeof date !== 'undefined') {
            $('#edit-task-time').val(() => {
                const [datePart, timePart] = date.split(' ');
                const [day, month, year] = datePart.split('/');
                const [hour, minute] = timePart.split(':');
                
                return `${year}-${month}-${day}T${hour}:${minute}`;
            });
        }
        
        data.categories.map((item) => {
            pushSelectItem($('.edit-task'), item.id, item.cateName, item.iconColor);
        })
    })
    .catch((error) => {})
})

$(document).ready(() => {
    $('.edit-task form').submit(function(e) {
        e.preventDefault();
        
        const id: string =  $(e.target).find('#edit-task-id').val();
        const name: string = $(e.target).find('#edit-task-name').val();
        const description: string = $(e.target).find('#edit-task-description').val();
        const date: string = $(e.target).find('#edit-task-time').val();
        const status: string = $(e.target).find('input[name="todo_status"]:checked').val();
        const categories = getSelectedValue($(this).closest('.form-box'));
        
        const submitBtn = $(e.target).find('.submit-form-btn');
        const btn_element: string = showButtonLoader(submitBtn);
        
        const params: URLSearchParams = getParams();
        
        $.ajax({
            url: `./api/todo?id=${id}&title=${name}&description=${description}&date=${date}&status=${status}&categories=${categories}`,
            type: 'PUT',
            success: (response) => {
                getTodayTodoList();
                getUpcomingTodoList();
                getFilterdTodoList();
                getCategoryTodoList(params.has('cid') ? params.get('cid') : "");
                    
                setTimeout(() => {
                    hideActionForm($('.edit-task-wrapper'));
                    hideOverlay();
                    hideButtonLoader(submitBtn, btn_element);
                }, 1000);
            },
            error: (error) => {
                console.error('Error when update todo.')
            }
        })
    })
})

$(document).on('click', '.task-status.not-complete, .completed-task-btn', function() {
    const id: string = $(this).data('id');
    const params: URLSearchParams = getParams();
    
    $.ajax({
        url: `./api/todo?t=status&id=${id}&status=completed`,
        type: 'PUT',
        success: (response) => {
            getTodayTodoList();
            getUpcomingTodoList();
            getFilterdTodoList();
            getCategoryTodoList(params.has('cid') ? params.get('cid') : "");
        },
        error: (error) => {
        }
    })
})

//Delete Todo
$(document).on('click', '.task-action.delete, .delete-task-btn', function() {
    showConfirmBox('Xác nhận xóa công việc này?', '', 'delete-todo', '#e30019');
    showOverlay();
    $('.task-infomation-wrapper').addClass('close');
    
    const id = $(this).data('id');
    const params: URLSearchParams = getParams();
    
    $('.confirm-btn').off('click').click(function() {
        const action = $(this).data('confirm');
        
        if(action === 'delete-todo') { 
            const btn = $(this);
            const btn_element = showButtonLoader(btn);
        
            $.ajax({
                url: `./api/todo?id=${id}`,
                type: 'DELETE',
                success: (response) => {
                    getTodayTodoList(); 
                    getUpcomingTodoList();
                    getFilterdTodoList();
                    getCategoryTodoList(params.has('cid') ? params.get('cid') : "");
                    
                    setTimeout(() => {
                        hideButtonLoader(btn, btn_element);
                        hideConfirmBox();
                        $('.task-infomation-wrapper').addClass('close');
                        setMainWidth();
                        hideOverlay();
                    }, 1000);
                },
                error: (error) => {
                    console.error(`Cannot delete todo ${id}`);
                }
            })
        }
    });
    
})

//Create sub todo
$('.add-sub-task').click(function() {
    const str: string = `<div class="create-subtask">
                            <form action="#" method="POST">
                                <input type="text" required autocomplete="off">
                                <button type="submit" class="submit-create-subtask">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m10 15.586-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z"></path>
                                    </svg>
                                </button>
                                <button type="button" class="cancel-create-subtask">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                        <path d="m16.192 6.344-4.243 4.242-4.242-4.242-1.414 1.414L10.535 12l-4.242 4.242 1.414 1.414 4.242-4.242 4.243 4.242 1.414-1.414L13.364 12l4.242-4.242z"></path>
                                    </svg>
                                </button>
                            </form>
                    </div>`;
                    
    $('.sub-task-list').append(str);
})

$(document).on('click', '.cancel-create-subtask', function() {
    $(this).closest('.create-subtask').remove();
})

$(document).on('submit', '.create-subtask form', function(e) {
    e.preventDefault();
    
    const todoId: string = $('.add-sub-task').data('id');
    const value: string = $(this).find('input').val();
    
    $.ajax({
        url: `./api/subtodo?id=${todoId}&title=${value}`,
        type: 'POST',
        success: (response) => {
            $(this).closest('.create-subtask').remove();
            loadSubTasks(todoId);
        },
        error: (error) => {
            console.error(`Cannot create sub task ${error}`);
        }
    })
})

//Edit subtodo
$(document).on('submit', '.edit-sub-task form', function(e) {
    e.preventDefault();
    const id = $(e.target).find('input[name="subtodo_id"]').val();
    const title = $(e.target).find('input[name="subtodo_title"]').val();
    
    $.ajax({
        url: `./api/subtodo?t=update&id=${id}&title=${title}`,
        type: 'PUT',
        success: (response) => {
            loadSubTasks($('.edit-task-btn').data('id'));
        },
        error: (error) => {
        }
    })
})

$(document).on('change', 'input[name="sub-task-cbox"]', function() {
    const complete: boolean = $(this).is(':checked');
    const id = $(this).val();
    
    $.ajax({
        url: `./api/subtodo?t=complete&id=${id}&complete=${complete}`,
        type: 'PUT',
        success: (response) => {
            loadSubTasks($('.edit-task-btn').data('id'));
        },
        error: (error) => {
        }
    })
})

//Get list subtodo
const loadSubTasks = (id: string) => {
    $.ajax({
        url: `./api/todo?id=${id}`,
        type: 'GET',
        success: (response) => {
            $('.sub-task-list').empty();
            
            let i = 0;
            response.subTodoList.forEach(subTodo => {
                i++;
                let str: string = `<div class="sub-task-item d-flex align-items-center">
                        <label for="sub-task-cbox-${i}" class="sub-task-label d-flex align-items-center gap-2">                         
                            <input type="checkbox" id="sub-task-cbox-${i}" value="${subTodo.id}" name="sub-task-cbox" ${subTodo.isCompleted ? 'checked' : ''}>
                            <span>${subTodo.title}</span>
                        </label>
                        <div class="edit-sub-task-btn">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                                <path d="M19.045 7.401c.378-.378.586-.88.586-1.414s-.208-1.036-.586-1.414l-1.586-1.586c-.378-.378-.88-.586-1.414-.586s-1.036.208-1.413.585L4 13.585V18h4.413L19.045 7.401zm-3-3 1.587 1.585-1.59 1.584-1.586-1.585 1.589-1.584zM6 16v-1.585l7.04-7.018 1.586 1.586L7.587 16H6zm-2 4h16v2H4z"></path>
                            </svg>
                        </div>
                        <div class="edit-sub-task d-flex align-items-center">
                            <form action="#" method="POST">
                                <input type="text" name="subtodo_title" value=${subTodo.title} required autocomplete="off">
                                <input type="hidden" name="subtodo_id" value="${subTodo.id}" required />
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
        },
        error: (error) => {
            
        }
    })
}

//Delete subtodo
$(document).on('click', '.delete-sub-task', function() {
    const id = $(this).data('id');

    $.ajax({
        url: `./api/subtodo?id=${id}`,
        type: 'DELETE',
        success: (response) => {
            loadSubTasks($('.edit-task-btn').data('id'));
        },
        error: () => {         
        }
    })
})


// -----------------------------------------------
//Category CRUD -------------
//Create category
$('.create-category form').submit((e) => {
    e.preventDefault();
    const name: string = $(e.target).find('#cate-name').val();
    const color: string = encodeURIComponent($(e.target).find('#cate-icon').val());
    
    const submitBtn = $(e.target).find('.submit-form-btn');
    const btn_element = showButtonLoader(submitBtn);
    
    $.ajax({
        url: `./api/categories?name=${name}&color=${color}`,
        type: 'POST',
        success: (response) => {
            setTimeout(() => {
                clearFormValue($(e.target));
                getCategories();
                hideButtonLoader(submitBtn, btn_element);
            }, 1000);
        },
        error: (error) => {
        }
    })
})

//GET categories
const getCategories = () => {
    $.ajax({
        url: './api/categories',
        type: 'GET',
        success: (response) => {
            if(response.length > 0) {
                $('.nav-categories').empty();
                response.map((item) => {
                    const str = `<li>
                                <div class="nav-link d-flex align-items-center justify-content-between">
                                    <a class="nav-link-box d-flex align-items-center text-decoration-none" href="./category?id=${item.id}">
                                        <span class="nav-color-icon" style="background: ${typeof item.iconColor !== 'undefined' ? item.iconColor : "#e30019"};"></span>

                                        <span class="nav-link-text">${item.cateName}</span>
                                    </a>
                                    
                                    <div class="nav-cate-action d-flex align-items-center gap-2">
                                        <i class='nav-edit-cate bx bx-edit' data-id="${item.id}"></i>
                                        <i class='nav-del-cate bx bx-trash' data-id="${item.id}"></i>
                                    </div>
                                </div>
                            </li>`;
                            
                     $('.nav-categories').append(str);
                })
                
                $('.nav-categories').append(`<li>
                        <a href="#" class="add-cate-action d-flex align-items-center">
                            <svg class="nav-icon" xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24">
                                <path d="M19 11h-6V5h-2v6H5v2h6v6h2v-6h6z"></path>
                            </svg>

                            Thêm danh mục
                        </a>
                    </li>`);
            }
            
        },
        error: (error) => {
        }
    })
}

//Update category
$(document).on('click', '.nav-edit-cate', function() {
    showActionForm($('.edit-category-wrapper'));
    showOverlay();
    
    const id = $(this).data('id');
    
    $.ajax({
        url: `./api/categories?id=${id}`,
        type: 'GET',
        success: (response) => {
            $('#edit-cate-id').val(response.id);
            $('#edit-cate-name').val(response.cateName);
            $('#edit-cate-icon').val(response.iconColor.length > 0 ? response.iconColor : '#e30019');
        },
        error: (error) => {
        }
    });
})

$('.edit-category form').submit(function(e) {
    e.preventDefault();
    const id: string = $(e.target).find('#edit-cate-id').val();
    const name: string = $(e.target).find('#edit-cate-name').val();
    const color: string = encodeURIComponent($(e.target).find('#edit-cate-icon').val());
    
    const submitBtn = $(e.target).find('.submit-form-btn');
    const btn_element = showButtonLoader(submitBtn);
    const params: URLSearchParams = getParams();
    
    $.ajax({
        url: `./api/categories?id=${id}&name=${name}&color=${color}`,
        type: 'PUT',
        success: (response) => {
            setTimeout(() => {
                getCategories();
                getTodayTodoList();
                getUpcomingTodoList();
                getFilterdTodoList();
                getCategoryTodoList(params.has('cid') ? params.get('cid') : "");
                        
                hideButtonLoader(submitBtn, btn_element);
                hideActionForm($('.edit-category-wrapper'))
                hideOverlay();
            }, 1000)
        },
        error: (error) => {
        }
    })
})

//DELETE category
$(document).on('click', '.nav-del-cate', function() {
    const id = $(this).data('id');
    const params: URLSearchParams = getParams();
    showConfirmBox('Xóa danh mục này?', 'Danh mục này và các công việc liên quan sẽ bị xóa.', 'delete-cate', '#e30019');
    showOverlay();
    
    $('.confirm-btn').off('click').click(function() {
        const action = $(this).data('confirm');
        
        if(action === 'delete-cate') { 
            const btn = $(this);
            const btn_element = showButtonLoader(btn);
            
            $.ajax({
                url: `./api/categories?id=${id}`,
                type: 'DELETE',
                success: (response) => {
                    setTimeout(() => {
                        getCategories();
                        hideButtonLoader(btn, btn_element);
                        hideConfirmBox();
                        
                        getTodayTodoList();
                        getUpcomingTodoList();
                        getFilterdTodoList();
                        getCategoryTodoList(params.has('cid') ? params.get('cid') : "");
                        
                        hideOverlay();
                    }, 1000);
                    
                },
                error: (error) => {
                    console.error(`Cannot delete category ${id}`);
                }
            })
        }
    })
})
