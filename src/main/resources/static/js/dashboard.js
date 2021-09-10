function showMonthEvents() {
	let request = new XMLHttpRequest();
	request.open('GET', `/api/cal/${currentYear}/${currentMonth + 1}`, true);
	request.onload = function() {
		if (this.status >= 200 && this.status < 400) {
	    	let data = JSON.parse(this.response);
	    	data.forEach(paintCalendarDay);
		}
	};
	request.send();
}

function paintCalendarDay(element, index, array) {
	$(`td:contains(${element[0]})`).addClass("event");
	$(`td:contains(${element[0]})`).append(`<span>${element[1]}</span>`);
}

$(document).on("click", "#calendar-body td:not(.empty)", showDayEvents);

function showDayEvents() {
	let day = $(this).text().substring(0, 2);
	let monthAndYear = $("#monthAndYear").text();
	$("aside>h3").text(`${day} ${monthAndYear}`);
	let request = new XMLHttpRequest();
	request.open('GET', `/api/cal/${currentYear}/${currentMonth + 1}/${day}`, true);
	request.onload = function() {
		$("aside > ul").empty();
		if (this.status >= 200 && this.status < 400) {
			let data = JSON.parse(this.response);
			if(data.length) {
				$("aside > div").hide();
			} else {
				$("aside > div").show();
			}
			data.forEach(paintDetailsDay);
		}
	};
	request.send();
}

function paintDetailsDay(event) {
	let dateFrom = new Date(event.dateFrom);
	let dateTo = new Date(event.dateTo);
	
	let timeFrom = formatTime(dateFrom.getHours()) + ":" + formatTime(dateFrom.getMinutes());
	let timeTo = formatTime(dateTo.getHours()) + ":" + formatTime(dateTo.getMinutes());
	
	let dayHTML = `
		<li>
			<strong>${timeFrom} - ${timeTo}</strong>
			<span>${event.eventName} - ${event.location}</span>
			<em>${event.name} (${event.phone} - ${event.email})</em>
		</li>
	`;
		
	$("aside > ul").append(dayHTML);
}

function formatTime(time) {
	return time.toString().padStart(2, '0');
}