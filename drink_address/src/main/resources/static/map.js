window.initMap = () => {
	
    let map;

    const area = document.getElementById("map"); // マップを表示させるHTMLの箱
	/*var latitude=parseFloat(document.getElementById("latitude").innerText);
	var longitude=parseFloat(document.getElementById("longitude").innerText);*/
	var table=document.getElementsByClassName('tableData');
	/*console.log(latitude);
	console.log(longitude);*/
	console.log(table);
	for(let i=0;i<table.length;i++){
		var latitude=table[i].childNodes[9].textContent;
		var longitude=table[i].childNodes[11].textContent;
		console.log(latitude);
		console.log(longitude);
		
	}
	
	var latitudes=[141.354401,139.665909];
	var longitudes=[43.062077,35.659534];
	
　　// マップの中心位置(例:原宿駅)
    var center = {
      lat: 141.354401,
      lng: 43.062077
	  
    };
	
	
	
	

	console.log(center);
    //マップ作成
    map = new google.maps.Map(area, {
      center,
      zoom: 13,
    });
    console.log("こんにちは");
        //マーカーオプション設定👇追記
    const markerOption = {
      position: center, // マーカーを立てる位置を指定
      map: map, // マーカーを立てる地図を指定
      icon: {
        url: 'https://1.bp.blogspot.com/-3wo33nLxIKw/X1LskM84NQI/AAAAAAABa_I/D7QLbbJYqOsyI26PwlE23Dl6Dy-CzM8rQCNcBGAsYHQ/s1600/drink_cola_petbottle.png',
  scaledSize: new google.maps.Size(35, 35) //👈追記
  }  
    }

    //マーカー作成
    const marker = new google.maps.Marker(markerOption); 
  }