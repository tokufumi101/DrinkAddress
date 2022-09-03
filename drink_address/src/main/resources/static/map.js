window.initMap = () => {
	console.log('こんにちわ');
    let map;

    const area = document.getElementById("map"); // マップを表示させるHTMLの箱
　　// マップの中心位置(例:原宿駅)
    const center = {
      lat: 35.667379,
      lng: 139.7054965
    };
	console.log(center);
    //マップ作成
    map = new google.maps.Map(area, {
      center,
      zoom: 17,
    });
console.log(map);
  }