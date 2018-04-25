function handleStarResult(resultData) {
	console.log("handleStarResult: populating star table from resultData");
	console.log(resultData);
	// populate the star table
	var starTableBodyElement = jQuery("#star_table_body");
	for (var i = 0; i < resultData.length; i++) 
	{
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th>" + resultData[i]["name"] + "</th>";
		rowHTML += "<th>" + resultData[i]["birthyear"] + "</th><th>";
		for (var j = 0; j <resultData[i]["movies"].length; j++)
		{
			rowHTML += "<a href='/Project2/singleMovie.html?movieId="+resultData[i]["movies"][j]["movieId"]+"'>" + resultData[i]["movies"][j]["movie"] + "</a> ";
		}
		rowHTML += "</th></tr>"
		starTableBodyElement.append(rowHTML);
	}
}

// makes the HTTP GET request and registers on success callback function handleStarResult
var url_string = window.location.href;
var url = new URL(url_string);
var id = url.searchParams.get("starId");
console.log(id)
jQuery.ajax({
	  dataType: "json",
	  method: "GET",
	  data:{starId:id},
	  url: "/Project2/SingleStar",
	  success: (resultData) => handleStarResult(resultData)
});