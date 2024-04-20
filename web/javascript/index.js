function capitalFirstWord(str) {
    return str.toLowerCase().split(' ').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}
function translate(str, targetLan) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'https://translation.googleapis.com/language/translate/v2',
            method: 'get',
            data: {
                q: str,
                target: targetLan,
                key: 'AIzaSyAQltwtMWmpslXsYUQr5By_OThNkn6Nxbs'
            },
            success: (respose) => {
                resolve(respose.data.translations[0].translatedText);
            },
            error: () => {
                reject(str);
            }
        });
    });
}
function updateCurrentTime() {
    let currentTime = new Date();
    let _time = currentTime.getHours();
    let date = currentTime.toLocaleDateString('en-GB');
    let time = currentTime.toLocaleTimeString('en-US');
    let daysOfWeek = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
    let day = daysOfWeek[currentTime.getDay()];
    if (_time >= 5 && _time <= 7) {
        $('.weather-box').css('background-image', `url('./images/Blood.png')`);
    }
    else if (_time > 7 && _time <= 17) {
        $('.weather-box').css('background-image', `url('./images/Sun.png')`);
    }
    else {
        $('.weather-box').css('background-image', `url('./images/Moon.png')`);
    }
    $('.date').text(day + ', ' + date);
    $('.clock').text(time);
}
function updateWeather(city, country) {
    const apiKey = '0292e39f8b40834fb7a306a3a3430ca4';
    const apiUrl = `https://api.openweathermap.org/data/2.5/weather?q=${city},${country}&units=metric&appid=${apiKey}`;
    $.ajax({
        url: apiUrl,
        method: 'get',
        success: (data) => {
            $('.temperature').text(Math.floor(data.main.temp) + ' °C');
            $('.city').text(data.name);
            translate(capitalFirstWord(data.weather[0].description), 'vi')
                .then(translatedText => {
                $('.weather-description').text(translatedText);
            })
                .catch(originalText => {
                $('.weather-description').text(originalText);
            });
            hideContentLoading($('.weather-box'));
        },
        error: () => {
            console.error('Cannot get weather data');
        }
    });
}
function loadWeatherBox() {
    setInterval(updateCurrentTime, 1000);
    showContentLoading($('.weather-box'));
    $('.temperature').text('... °C');
    $('.weatherIcon').append(`<div class="mostlysunny"><div class="inner"></div></div>`);
    $('.city').text('...');
    const ipUrl = 'https://ipinfo.io/json';
    $.ajax({
        url: ipUrl,
        method: 'get',
        success: (data) => {
            const city = data.city;
            const country = data.country;
            updateWeather(city, country);
        },
        error: () => {
            console.error('Cannot get location.');
        }
    });
}
$(document).ready(() => {
    loadWeatherBox();
});
$('.reload-weather').click(() => {
    loadWeatherBox();
});
