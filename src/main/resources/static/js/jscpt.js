const week = ["日", "月", "火", "水", "木", "金", "土"];
const today = new Date(); //今日の日付けを獲得
// 月末だとずれる可能性があるため、1日固定で取得
var showDate = new Date(today.getFullYear(), today.getMonth(), 1); //今日の年と月と日にち（１日）を代入

var expenseJs = new Array() ; //支出のリストを保管 11/21
var incomeJs = new Array() ; //収入のリストを保管 11/21

// 祝日取得
var request;
window.onload = function () {
    request = new XMLHttpRequest();
    request.open('get', 'csv/syukujitsu.csv', true);
    request.send(null);
    request.onload = function () {
        // 初期表示
        showProcess(today, calendar);
    };
};



// 前の月表示
function prev(){
    showDate.setMonth(showDate.getMonth() - 1);
    showProcess(showDate);
}

// 次の月表示
function next(){
    showDate.setMonth(showDate.getMonth() + 1);
    showProcess(showDate);
}
//------------------------------------------------------ カレンダー　収入・支出を表示　11/21 新崎
function price(table){
	//var expenseJs = new Array() ; //支出 
	//var incomeJs = new Array() ; //収入
	//var incom = new Array() ; //収入
	var j = 0;
	var k = 0; 

	for(var i = 0; i < table.length ;i++){
		//支出を獲得
		if(table[i]["name"] !== null){
			expenseJs[j] = table[i];
			j++;
			
		}else if(table[i]["income"] !== null){
			incomeJs[k] = table[i];
			k++;
		}
	}
	
	return 10;
}

//-----------------------------------------------------------
// カレンダー表示
function showProcess(date) {
    var year = date.getFullYear();
    var month = date.getMonth(); // 0始まり
    document.querySelector('#header').innerHTML = year + "年 " + (month + 1) + "月";

    var calendar = createProcess(year, month);
    document.querySelector('#calendar').innerHTML = calendar;
}

//11/21------------
function expense1(year, month,count){
	var dayExpense = 0 ;
	month+=1;
	if(10>count){
		count = "0" +count;
	}
	//console.log(month)
	if(10>month){
		month = "0" +month;
	}
	for(var i =0; i < expenseJs.length;i++){
		if(expenseJs[i]["date"] == (year + "-" + month + "-" + count)){ 
			dayExpense += expenseJs[i]["price"]
		}
	}
	if(dayExpense == 0){
		return "<div class='expense'>"+"&nbsp;"+"</div>"
	}else{
		return "<div class='expense'>" + dayExpense + "</div>";
	}
}
function income1(year, month,count){
	var dayIncome = 0 ;
	month+=1;
	if(10>count){
		count = "0" +count;
	}
	if(10>month){
		month = "0" +month;
	}
	for(var i =0; i < incomeJs.length;i++){
		if(incomeJs[i]["date"] == (year + "-" + month + "-" + count)){ 
			dayIncome +=  incomeJs[i]["price"] ;
		}
	}
	if(dayIncome == 0){
		return "<div class='income'>"+"&nbsp;"+"</div>"
	}else{
		return "<div class='income'>" + dayIncome + "</div>";
	}
	console.log(incomeJs);
}
//------------
// カレンダー作成
function createProcess(year, month) {
    // 曜日
    var calendar = "<table><tr class='dayOfWeek'>";
    for (var i = 0; i < week.length; i++) {
        calendar += "<th>" + week[i] + "</th>";
    }
    calendar += "</tr>";

    var count = 0;
    var startDayOfWeek = new Date(year, month, 1).getDay();
    var endDate = new Date(year, month + 1, 0).getDate();
    var lastMonthEndDate = new Date(year, month, 0).getDate();
    var row = Math.ceil((startDayOfWeek + endDate) / week.length);
	//var expense = new Array(); //17日　年と月が一致した支出
	//var income= new Array();  //17日　年と月が一致し収入　
	//var date;
	

	
    // 1行ずつ設定
    for (var i = 0; i < row; i++) {
        calendar += "<tr>";
        // 1colum単位で設定
        for (var j = 0; j < week.length; j++) {
            if (i == 0 && j < startDayOfWeek) {
                // 1行目で1日まで先月の日付を設定
                calendar += "<td class='disabled'>" + (lastMonthEndDate - startDayOfWeek + j + 1) + "</td>";
            } else if (count >= endDate) {
                // 最終行で最終日以降、翌月の日付を設定
                count++;
                calendar += "<td class='disabled'>" + (count - endDate) + "</td>";
            } else {
                // 当月の日付を曜日に照らし合わせて設定
                count++;
                var dateInfo = checkDate(year, month, count);
                if(dateInfo.isToday){
                    calendar += "<td class='today'>" + count + "<br>"+  expense1(year, month,count) + income1(year, month,count)
					 +"<a href='/kakeibo_amount?year=" + year + "&month=" + (month + 1) + "&day=" + count + "'</td>";
                } else if(dateInfo.isHoliday) {
                    calendar += "<td class='holiday' title='" + dateInfo.holidayName + "'>" + count + "<br>"+  expense1(year, month,count) + income1(year, month,count)
					+ "<a href='/kakeibo_amount?year=" + year + "&month=" + (month + 1) + "&day=" + count + "'></a></td>";
                } else {
                    calendar += "<td>" + count + "<br>"+  expense1(year, month,count) + income1(year, month,count)
					 + "<a href='/kakeibo_amount?year=" + year + "&month=" + (month + 1) + "&day=" + count + "'></a></td>";
                }
            }
        }
        calendar += "</tr>";
    }
    return calendar;
}

// 日付チェック
function checkDate(year, month, day) {
    if(isToday(year, month, day)){
        return {
            isToday: true,
            isHoliday: false,
            holidayName: ""
        };
    }

    var checkHoliday = isHoliday(year, month, day);
    return {
        isToday: false,
        isHoliday: checkHoliday[0],
        holidayName: checkHoliday[1],
    };
}

// 当日かどうか
function isToday(year, month, day) {
    return (year == today.getFullYear()
        && month == (today.getMonth())
        && day == today.getDate());
    }

// 祝日かどうか
function isHoliday(year, month, day) {
    var checkDate = year + '/' + (month + 1) + '/' + day;
    var dateList = request.responseText.split('\n');
    // 1行目はヘッダーのため、初期値1で開始
    for (var i = 1; i < dateList.length; i++) {
        if (dateList[i].split(',')[0] === checkDate) {
            return [true, dateList[i].split(',')[1]];
        }
    }
    return [false, ""];
}
//  11/16

function iscategory(){
	
	return (location.href='http://localhost:8080/kakeibo_category');
        }

