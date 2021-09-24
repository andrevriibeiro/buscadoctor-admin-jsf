/**
 * @author André Ribeiro, William Suane on 3/31/17.
 */

function disableAllTheseDays(date) {
    //disabledDays se encontra no template.xhtml
    var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
    for (var i = 0; i < disabledDays.length; i++) {
        if ($.inArray(y + '-' + (m + 1) + '-' + d, disabledDays) !== -1) {
            return [false];
        }
    }
    return [true];
}
function enableAllTheseDays(date) {
    var m = date.getMonth(), d = date.getDate(), y = date.getFullYear();
    for (var i = 0; i < enabledDays.length; i++) {
        if ($.inArray(y + '-' + (m + 1) + '-' + d, enabledDays) !== -1) {
            return [true];
        }
    }
    return [false];
}

//Início Hora
function tpStartOnHourShowCallback(hour) {
    if (!PrimeFaces.widgets['endTimeWidget']) {
        return false;
    }

    var tpEndHour = parseInt(PF('endTimeWidget').getHours());

    // Check if proposed hour is prior or equal to selected end time hour
    if (parseInt(hour) <= tpEndHour || tpEndHour === -1) {
        return true;
    }

    // if hour did not match, it can not be selected
    return false;
}

//Fim Hora
function tpEndOnHourShowCallback(hour) {
    if (!PrimeFaces.widgets['startTimeWidget']) {
        return false;
    }

    var tpStartHour = parseInt(PF('startTimeWidget').getHours());

    // Check if proposed hour is after or equal to selected start time hour
    if (parseInt(hour) >= tpStartHour) {
        return true;
    }
    // if hour did not match, it can not be selected
    return false;
}
//Início Minuto
function tpStartOnMinuteShowCallback(hour, minute) {
    if (!PrimeFaces.widgets['endTimeWidget']) {
        return false;
    }

    var tpEndHour = parseInt(PF('endTimeWidget').getHours());
    var tpEndMinute = parseInt(PF('endTimeWidget').getMinutes());

    // Check if proposed hour is prior to selected end time hour
    if (parseInt(hour) < tpEndHour) {
        return true;
    }

    // Check if proposed hour is equal to selected end time hour and minutes is prior
    if ((parseInt(hour) === tpEndHour) && (parseInt(minute) < tpEndMinute)  ) {
        return true;
    }
    if(tpEndHour === -1 && tpEndMinute === 0){
        return true;
    }
    // if minute did not match, it can not be selected
    return false;
}


//Fim Minuto
function tpEndOnMinuteShowCallback(hour, minute) {
    if (!PrimeFaces.widgets['startTimeWidget']) {
        return false;
    }

    var tpStartHour = parseInt(PF('startTimeWidget').getHours());
    var tpStartMinute = parseInt(PF('startTimeWidget').getMinutes());

    // Check if proposed hour is after selected start time hour
    if (parseInt(hour) > tpStartHour) {
        return true;
    }

    // Check if proposed hour is equal to selected start time hour and minutes is after
    if ((parseInt(hour) === tpStartHour) && (parseInt(minute) > tpStartMinute)) {
        return true;
    }

    // if minute did not match, it can not be selected
    return false;
}