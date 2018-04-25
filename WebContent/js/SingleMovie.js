function handleStarResult(resultData,id) {
	console.log("handleStarResult: populating star table from resultData");
	console.log(resultData);
	var result= resultData["responseText"].substring(id.length);
	console.log(result);
	var data = JSON.parse(result);
	console.log(data);
	// populate the star table
	var movieTableBodyElement = jQuery("#movie_table_body");
	for (var i = 0; i < data.length; i++) 
	{
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th><a href='/Project2/singleMovie.html?movieId="+data[i]["id"]+"'>" + data[i]["title"] + "</a></th>";
		rowHTML += "<th>" + data[i]["year"] + "</th>";
		rowHTML += "<th>" + data[i]["director"] + "</th>";
		rowHTML += "<th>" + data[i]["rating"] + "</th><th>";
		for (var j = 0; j <data[i]["genres"].length; j++)
		{
			rowHTML += "" + data[i]["genres"][j]["genre"] + "  ";
		}
		rowHTML += "</th>";
		
		rowHTML += "<th>";

		for (var k = 0; k <data[i]["stars"].length; k++)
		{
			rowHTML += "<a href='/Project2/SingleStar.html?starId="+data[i]["stars"][k]["starId"]+"'>" + data[i]["stars"][k]["star"] + "</a>  ";
		}
		
		rowHTML += "</th>"
			rowHTML += '<th><form class="add-to-cart" action="/Project2/cart.html" method="get"><label for="qty-1">Quantity</label><input type="text" name="qty-1" id="qty-1" class="qty" value="1" /><input hidden type="text" name="movieid" value="'+data[i]["id"]+'" /><p><input type="submit" value="Add to cart" class="btn" /></p></form></th></tr>';
		movieTableBodyElement.append(rowHTML);
	}
}

// makes the HTTP GET request and registers on success callback function handleStarResult
$(document).ready(function() {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var id = url.searchParams.get("movieId");
	console.log(id)
	jQuery.ajax({
		  dataType: "json",
		  method: "GET",
		  data:{movieId:id},
		  url: "/Project2/SingleMovie",
		  complete: (resultData) => handleStarResult(resultData,id)
	});
});