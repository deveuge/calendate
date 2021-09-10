var currentTab = 0;
(function() {
    if(document.getElementById('alert') != undefined && document.getElementById('alert').innerHTML.length) {
        showAlert();
    }
 })();
 
 /* ALERTS */
function showAlert() {
    var alert = document.getElementById('alert');
    alert.classList.add('active');
    setTimeout(function(){
        alert.classList.add('done');
    }, 2900);
}

/* MODAL */
var modal = document.getElementById("modal");

function openModal(dataValue) {
	modal.classList.add('active');
	modal.setAttribute('data-attr', dataValue);
}

function closeModal() {
	modal.classList.add('done');
	setTimeout(function(){
		modal.classList.remove('active', 'done');
    }, 400);
}

window.onclick = function(event) {
	if (event.target == modal) {
		closeModal();
	}
}

function confirmModal(type) {
	var data = modal.getAttribute('data-attr');
}

/* TOGGLE LOGIN FORM */
function toggleSignIn(e, isLogin) {
	e.classList.toggle('active')
	if(isLogin) {
		e.nextElementSibling.classList.toggle('active');
	} else {
		e.previousElementSibling.classList.toggle('active');
	}
	document.getElementById('signin-form').classList.toggle('active');
	document.getElementById('signup-form').classList.toggle('active');
}

/* MOBILE MENU */
function openMenu(e) {
    e.nextElementSibling.classList.toggle('active');
}

/* FORM NAVIGATION */
function showTab(n, useAside) {
    var x = document.getElementsByClassName("tab");
    x[n].style.display = "block";
    if(useAside) {
    	showAside(n);
    }

    document.getElementById("btn-prev").style.display = n == 0 ? 'none' : 'block';
    document.getElementById("btn-next").style.display = (n == (x.length - 1) ? 'none' : 'block');
    document.getElementById("btn-submit").style.display = (n == (x.length - 1) ? 'block' : 'none');
}

function showAside(n) {
    var y = document.getElementsByTagName("aside");
    y[n].style.display = "flex";
}

function changePage(n, useAside) {
    var x = document.getElementsByClassName("tab");
    
    if (n == 1 && !validateForm()) {
        var form = document.getElementById("event-form");
        if (!form.checkValidity()) {
            var tmpSubmit = document.createElement('button');
            form.appendChild(tmpSubmit);
            tmpSubmit.click();
            form.removeChild(tmpSubmit);
        } 
        return false;
    }
    
    if(useAside) {
    	changeAside();
    }
    
    x[currentTab].style.display = "none";
    
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
        return false;
    }
    showTab(currentTab, useAside);
}

function changeAside() {
    var y = document.getElementsByTagName("aside");
    y[currentTab].style.display = "none";
}

function validateForm() {
    var x, y, i;
    x = document.getElementsByClassName("tab");
    y = x[currentTab].getElementsByTagName("input");
    for (i = 0; i < y.length; i++) {
        if (!y[i].checkValidity()) {
            return false;
        }
    }
    return true;
}

function updateInput(id, value) {
	$(`#${id}-confirm`).val(value);
}