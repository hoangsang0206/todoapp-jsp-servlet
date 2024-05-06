$('.lang-select-btn').click(() => {
    $('.lang-select-content').toggleClass('show');
})

$('.setting-side-item').click(function() {
    $('.setting-side-item').removeClass('active');
    $(this).addClass('active');
    
    let data = $(this).data('setting');
    let elements = $('.setting-content-item').toArray();
    elements.map((element) => {
        if($(element).data('setting') === data) {
            $('.setting-content-item').removeClass('current');
            $(element).addClass('current');
        }
    })
})