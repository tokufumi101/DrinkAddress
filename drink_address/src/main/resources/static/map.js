function initMap() {
	 let map;
    var marker = [];
      tokyo = new google.maps.LatLng(35.689614,139.691585);
	  var opts = {
    zoom: 4,
    center: tokyo
  };
  map = new google.maps.Map(document.getElementById("map"), opts);
  var infoWindow=[];
	var table=document.getElementsByClassName('tableData');
	for(let i=0;i<table.length;i++){
		//markerLatLng = new google.maps.LatLng({lat: parseFloat(table[i].childNodes[11].textContent), lng: parseFloat(table[i].childNodes[9].textContent)});
		var latitude=parseFloat(table[i].childNodes[11].textContent);
		var longitude= parseFloat(table[i].childNodes[9].textContent);
		var myLatLng = {lat: latitude,lng:longitude};
		console.log(latitude);
		marker[i] = new google.maps.Marker({ // マーカーの追加
         position: myLatLng, // マーカーを立てる位置を指定
            map: map // マーカーを立てる地図を指定
       });
		infoWindow[i]=new google.maps.InfoWindow({
			/*content:document.getElementById("drinkName").childNodes.values.toString*/
			content:'<div class="sample">'+ table[i].childNodes[5].textContent + '</div>'
		})
		markerEvent(i);//マーカーにクリックイベントを追加
	}
	function markerEvent(i){
		marker[i].addListener('click',function(){
			infoWindow[i].open(map,marker[i]);
		});
	}
   /*     //マーカーオプション設定👇追記
    const markerOption = {
      position: center, // マーカーを立てる位置を指定
      map: map, // マーカーを立てる地図を指定
      icon: {
        url: 'https://1.bp.blogspot.com/-3wo33nLxIKw/X1LskM84NQI/AAAAAAABa_I/D7QLbbJYqOsyI26PwlE23Dl6Dy-CzM8rQCNcBGAsYHQ/s1600/drink_cola_petbottle.png',
  scaledSize: new google.maps.Size(35, 35) //👈追記
  }  
    }

    //マーカー作成
    //const marker = new google.maps.Marker(markerOption); */
  //}
  }