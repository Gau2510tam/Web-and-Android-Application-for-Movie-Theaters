var updown=0;
var pos=1;
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

function AutohandleResult(resultData,title) {
	console.log("SearchResult: populating movie table from resultData");
	updown=0;
	pos=1;
	/*data = JSON.parse(resultData);
	console.log(data);
	var userList = new List('movies', options, data);*/
	//System.out.println(data);
	console.log(resultData);
	localStorage[title] =JSON.stringify(resultData); // Give input in this also and maintain cache.
	var searchBodyElement = jQuery("#auto_table_body");
	var check = true;
	var rowHTML = "";
	
	rowHTML += "<tr><th> MOVIES </th></tr>";
	searchBodyElement.append(rowHTML);
	for (var i = 0; i < resultData.length; i++) 
	{if(resultData[i]["id"] == "entry")
		{
		check = false;
	
		var rowHTML = "";
		
		rowHTML += "<tr><th> STARS </th></tr>";
		searchBodyElement.append(rowHTML);
		
		
		}
		
	else{
		updown++;
	if(check)
		{var rowHTML = "";
		rowHTML += "<tr class='updownclass' id='updown-"+updown+"'>";
		rowHTML += "<th><a href='/Project2/singleMovie.html?movieId="+resultData[i]["id"]+"'>" + resultData[i]["title"] + "</a></th>";
		rowHTML += "</tr>";
		searchBodyElement.append(rowHTML);
		}
	else {
		var rowHTML = "";
		rowHTML += "<tr class='updownclass' id='updown-"+updown+"'>";
		rowHTML += "<th><a href='/Project2/SingleStar.html?starId="+resultData[i]["id"]+"'>" + resultData[i]["name"] + "</a></th>";
		rowHTML += "</tr>";
		searchBodyElement.append(rowHTML);
		
         }
	}
	}
}
 
 $(document).ready(function() 
		 { 
	 
	 $('#form1').click(function() {
		 $("#search_table_body").html("");
			$("#auto_table_body").html("");
			 var title = $("#title").val();
			 var year = $("#year").val();
			 var director = $("#director").val();
			 var starname = $("#starname").val();
			 var sort = $("#sort").val();

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
	 
	 var keycount = 0;
	 
	 $('#title').keydown(function(e) {
		console.log(e.which);
		 if(e.which == 38)
			{console.log("UP");
			 if(pos == 1)
				{pos=updown;
				}
			 else 
				 {
				  pos--;
				 }
			 
			 $("#title").val($("#updown-" + pos).find("a").text());
			 $(".updownclass").css("background-color" , "white");
			 $("#updown-" + pos).css("background-color" , "#5bc0de");
			}
		else if(e.which == 40)
			{
			console.log("DOWN");
			 if(pos == updown)
				{pos=1;
				}
			 else 
				 {
				  pos++;
				 }
			 $("#title").val($("#updown-" + pos).find("a").text());
			 $(".updownclass").css("background-color" , "white");
			 $("#updown-" + pos).css("background-color" , "#5bc0de");
			}
		else if(e.which == 13)
			{
			var url =  $("#updown-" + pos).find("a").attr("href");
			window.location = url;
			}
		
		else
		 { keycount++;
			var delayInMilliseconds = 300; //1 second

			setTimeout(function(keyc) {
			   //your code to be executed after 1 second
				console.log(keyc);
				if(keycount== keyc){
					$("#auto_table_body").html("");
					var title = $("#title").val();
					if(title.length>2)
					{   console.log('AutoComplete Search is initiated');
						var myVar = localStorage[title] || 'some';
						if(myVar == 'some')
				{
					
						 console.log(title);
				         console.log('Ajax request being done');
						
							$.ajax({
					     		dataType: "json",
				         	method: "GET",
				         	url: "/Project2/SearchAuto",
				         	data:{title: title},
				         	success: (resultData) => AutohandleResult(resultData,title)
					 	
				    	//data = JSON.parse(resultData);
				 	   // var userList = new List('movies', options, data);
				        
				     	});
				}	
						else
							{
							 ArrayData = JSON.parse(myVar);
							 console.log('Data from Cache');
							 console.log(title);
							 console.log(ArrayData);
							 AutohandleResult(ArrayData,title);
							}
				}
				}
			}, delayInMilliseconds,keycount);
		
		 }
		});
	    
		}); 
		 
 
 
 
 
 
 
 
