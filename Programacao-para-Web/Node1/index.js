const fs = require('fs');
const http = require('http');

http.createServer(function(req, res){

    var x;

    process.argv.forEach((val, index) => {
        if(index < 2){
            x = './';
        }else if(index == 2){
            x = val;
        }
    });

    console.log(x);

    fs.readdir(`${x}/`, (err, files) => {
        if(err){
            res.write('Algo de Errado Aconteceu');
        }
        else{
            files.forEach((val, index) => {
                res.write(`${val}\n`);
            });
        }
        res.end();
    });

}).listen(8000);