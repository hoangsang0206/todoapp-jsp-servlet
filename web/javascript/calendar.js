let today = new Date();
let month = today.getMonth();
let year = today.getFullYear();
let currentDay;
const daysOfWeek = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
const months = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];
const initCalendar = () => {
    currentDay = new Date(year, month, today.getDate());
    let date = months[currentDay.getMonth()] + ', ' + currentDay.getFullYear();
    $('.current-day').text(date);
    let lastDayOfMonth = new Date(year, month + 1, 0);
    let firstDayOfMonth = new Date(year, month, 1);
    let prevLastDay = new Date(year, month, 0);
    let prevDays = prevLastDay.getDate();
    let lastDate = lastDayOfMonth.getDate();
    let day = firstDayOfMonth.getDay();
    let nextDays = 7 - lastDayOfMonth.getDay() - 1;
    let calendarEmelemts = "";
    $('.c-days').empty();
    for (let i = day; i > 0; i--) {
        calendarEmelemts += `<div class="c-day">${prevDays - i + 1}</div>`;
    }
    for (let i = 1; i <= lastDate; i++) {
        if (i === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
            calendarEmelemts += `<div class="c-day current">${i}</div>`;
        }
        else {
            calendarEmelemts += `<div class="c-day">${i}</div>`;
        }
    }
    for (let i = 1; i <= nextDays; i++) {
        calendarEmelemts += `<div class="c-day">${i}</div>`;
    }
    $('.c-days').append(calendarEmelemts);
};
initCalendar();
const prevMonth = () => {
    month--;
    if (month < 0) {
        month = 11;
        year--;
    }
    initCalendar();
};
const nextMonth = () => {
    month++;
    if (month > 12) {
        month = 1;
        year++;
    }
    initCalendar();
};
const todayCalendar = () => {
    month = today.getMonth();
    year = today.getFullYear();
    initCalendar();
};
$('.prev-month').click(prevMonth);
$('.next-month').click(nextMonth);
$('.btn-today').click(todayCalendar);
