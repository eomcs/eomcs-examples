const express = require('express');
const request = require('request');

const port = 3000;
const app = express();

app.use(express.urlencoded({extended: true}));


app.get('/hello', (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Content-Type', 'text/plain; charset=UTF-8');

  setTimeout(()=> {
    res.send("Hello!");
  }, 3000);
}); 

app.get('/header', (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Content-Type', 'text/html; charset=UTF-8');

  res.send('<h1>비트캠프 네이버 클라우드 AIaaS 개발자 양성과정</h1>');
}); 

app.get('/footer', (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Content-Type', 'text/html; charset=UTF-8');

  res.send('<address>비트캠프 서초캠프@2022</address>');
}); 

app.get('/boards', (req, res) => {
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Content-Type', 'text/html; charset=UTF-8');
  let arr = [
    {no:1,title:'제목1',writer:'홍길동',viewCnt:19},
    {no:2,title:'제목2',writer:'임꺽정',viewCnt:312},
    {no:3,title:'제목3',writer:'유관순',viewCnt:31},
    {no:4,title:'제목4',writer:'안중근',viewCnt:100},
    {no:5,title:'제목5',writer:'윤봉길',viewCnt:200}
  ];

  res.send(JSON.stringify(arr));

}); 

app.get('/proxy', (req, res) => {    

  res.set('Access-Control-Allow-Origin', '*');
  res.set('Content-Type', 'text/plain; charset=UTF-8');

  request.get({
      uri: req.query.url
    }, (error, response, body) => {
      res.send(body);
  });
}); 

app.get('/weather', (req, res) => {    
  res.set('Access-Control-Allow-Origin', '*');
  res.set('Content-Type', 'application/json; charset=UTF-8');

  let openApiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?" +
    "serviceKey=이 자리에 각자가 받은 인증코드 넣을 것" + 
    "&pageNo=1" + 
    "&numOfRows=1000" + 
    "&dataType=JSON" + 
    "&base_date=" + req.query.base_date + // 조회 날짜 
    "&base_time=0600" + 
    "&nx=" + req.query.nx +  // 지역 X 좌표
    "&ny=" + req.query.ny;  // 지역 Y 좌표

    request.get({
      uri: openApiUrl
    }, (error, response, body) => {
      res.send(body);
  });
}); 

app.listen(port, () => {
  console.log(`${port}번 포트에서 서버 시작했음!`);
});

