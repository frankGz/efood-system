function AddToCart(number){
		var request = new XMLHttpRequest();
		request.onreadystatechange = function() {
			if (request.readyState==4&&request.status==200)
		    {
		        alert(xmlhttp.responseText);
		    }
		};
		request.open("GET", ("Cart?add=" + number), true);
	    request.send(null);
}