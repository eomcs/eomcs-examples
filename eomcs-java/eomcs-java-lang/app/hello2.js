var arr = ["aaa", "bbb", true, 100, new Object(), function() {}];


console.log(arr.length);
console.log(arr);
console.log("-------------------");

arr[8] = false;
console.log(arr.length);
console.log(arr);