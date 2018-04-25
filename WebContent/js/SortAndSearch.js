var options = {
  page:2, pagination:true,
  valueNames: [ 'id','title', 'year','director','genres','stars' ],
  item: '<li class="name">\
	  <div class="name"><h3 class="name"></h3></div>\
	  <div><a href="/Project2/singleMovie.html" class="title"> details</a></div>\
	  <div class="form-group"><label>year</label><p class="year"></p></div>\
	  <div class="form-group"><label>director</label><p class="director"></p></div>\
	  <div class="form-group"><label>genres</label><p class="genres"></p></div>\
	  <div class="form-group"><label>rating</label><p class="rating"></p></div>\
	  <div class="form-group"><label>stars</label><p class="stars"></p></div>\
	  <div><a href="/Project2/SingleStar.html" class="stars"> See all stars</a></div>\
	  <form class="add-to-cart" action="/Project2/cart.html" method="post">\
      		<label for="qty-1">Quantity</label> \
	  		<input type="text" name="qty-1" id="qty-1" class="qty" value="1" />\
       		<p><input type="submit" value="Add to cart" class="btn" /></p>\
	  </form>\
	  </li>'
};

function handleResult(resultData) {
	console.log("SearchResult: populating movie table from resultData");
	/*data = JSON.parse(resultData);
	console.log(data);
	var userList = new List('movies', options, data);*/
	//System.out.println(data);

	var searchBodyElement = jQuery("#search_table_body");
	for (var i = 0; i < resultData.length; i++) 
	{
		var rowHTML = "";
		rowHTML += "<tr>";
		rowHTML += "<th><a href='/Project2/singleMovie.html?movieId="+resultData[i]["id"]+"'>" + resultData[i]["title"] + "</a></th>";
		rowHTML += "<th>" + resultData[i]["year"] + "</th>";
		rowHTML += "<th>" + resultData[i]["director"] + "</th>";
		rowHTML += "<th>" + resultData[i]["rating"] + "</th><th>";
		for (var j = 0; j <resultData[i]["genres"].length; j++)
		{
			rowHTML += "" + resultData[i]["genres"][j]["genre"] + "  ";
		}
		rowHTML += "</th>";
		
		rowHTML += "<th>";

		for (var k = 0; k <resultData[i]["stars"].length; k++)
		{
			rowHTML += "<a href='/Project2/SingleStar.html?starId="+resultData[i]["stars"][k]["starId"]+"'>" + resultData[i]["stars"][k]["star"] + "</a>  ";
		}
		
		rowHTML += "</th>"
		rowHTML += '<th><form class="add-to-cart" action="/Project2/cart.html" method="get"><label for="qty-1">Quantity</label><input type="text" name="qty-1" id="qty-1" class="qty" value="1" /><input hidden type="text" name="movieid" value="'+resultData[i]["id"]+'" /><p><input type="submit" value="Add to cart" class="btn" /></p></form></th></tr>';
		searchBodyElement.append(rowHTML);
	}
}

/*jQuery.ajax({
	  dataType: "json",
	  method: "GET",
      data:{field1: "title",field2: "year",field3: "director",field4: "starname"},
	  url: "/Project2/Searching",s
	  success: (resultData) => SearchResult(resultData)
});*/

 
 $(document).ready(function() 
		 { 
	 
	 $('#form1').click(function() {
		 $("#search_table_body").html("");
			 var title = $(".aform").find("#title").val();
			 var year = $(".aform").find("#year").val();
			 var director = $(".aform").find("#director").val();
			 var starname = $(".aform").find("#starname").val();
			 var sort = $(".aform").find("#sort").val();

			 	 $.ajax({
		    	 dataType: "json",
		         method: "GET",
		         url: "/Project2/Searching",
		         data:{title: title,year: year, director: director, starname: starname, sort:sort},
		         success: (resultData) => handleResult(resultData)

		    	//data = JSON.parse(resultData);
		 	   // var userList = new List('movies', options, data);
		        
		     });

		 });
		}); 
