
//photoViewer after picture picked from computer
function refreshPicBeforeInput(event) {
  var reader = new FileReader();
  reader.onload = function(){
    var output = document.getElementById('output');
    output.src = reader.result;
  };
   reader.readAsDataURL(event.target.files[0]);
};