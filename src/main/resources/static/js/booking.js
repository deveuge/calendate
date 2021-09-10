function showMonthEvents() {
	let request = new XMLHttpRequest();
	request.open('GET', `/api/aval/${username}/${url}/${currentYear}/${currentMonth + 1}`, true);
	request.onload = function() {
		if (this.status >= 200 && this.status < 400) {
	    	let data = JSON.parse(this.response);
	    	data.forEach(paintCalendarDay);
		}
	};
	request.send();
}

function paintCalendarDay(element) {
	$(`td:contains(${element})`).addClass("active");
}

$(document).on("click", "#calendar-body td.active", showAvailableHours);

function showAvailableHours() {
	let day = $(this).text();
	let month = String(currentMonth + 1).padStart(2, '0');
	let monthAndYear = $("#monthAndYear").text();
	
	$("#calendar-body td.selected").removeClass("selected");
	$(this).addClass("selected");
	
	$("input#date").val(`${currentYear}-${month}-${day}`).change();
	$("input#hour").val('').change();
	$("#btn-next").addClass("hidden");
	
	$("#calendar-wrapper").hide();
	$("#event-form").show();
	changePage(1);
	
	let request = new XMLHttpRequest();
	request.open('GET', `/api/aval/${username}/${url}/${currentYear}/${currentMonth + 1}/${day}`, true);
	request.onload = function() {
		$("#hours-list").empty();
		if (this.status >= 200 && this.status < 400) {
			let data = JSON.parse(this.response);
			data.forEach(paintAvailableHours);
		}
	};
	request.send();
}

function paintAvailableHours(data) {
	$("#hours-list").append(`<button type="button">${data}</button>`);
}

$(document).on("click", "#hours-list > button", selectHour);

function selectHour() {
	$("#hours-list > button.selected").removeClass("selected");
	$("#btn-next").removeClass("hidden");
	$(this).addClass("selected");
	$("input#hour").val($(this).text()).change();
	changePage(1);
}