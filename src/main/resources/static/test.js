const input = document.querySelector(".input-field")
const resultArea = document.querySelector(".newDiv")
const resultAreaFel = document.querySelector(".felDiv")

function removeElement() {
  document.getElementById("imgbox1").style.display = "none";
}

function myFunction() {
  var s = document.getElementById('engSvar');
    s.style.display = 'block';
}

function mySecondFunction() {
  var x = document.querySelector(".eng");
x.style.display = 'none';
}

function visaSvar(s) {

  var x = document.querySelector(".newDiv");
  var y = document.querySelector(".felDiv");
  var input = document.querySelector(".input-field")
  console.log("input field: ", input)
  console.log("skickad parameter: ", s)
  x.style.display = 'none';
  y.style.display = 'none';
  if (input.value === s){
   x.style.display = 'block';
  } else {
  y.style.display = 'block';
  }


}

