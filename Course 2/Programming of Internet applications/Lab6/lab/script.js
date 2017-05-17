const onButtonClick = (event) => {
  event.preventDefault();

  const button = document.getElementById('R');
  button.innerHTML = 1 + button.innerHTML % 5;
};

const onTextInput = (event, value) => {
  event.preventDefault();

  const regExp = /^[+-]?\d([\.,]\d*)?$/;  
  const input = document.getElementById('Y');
  const result = value.match(regExp);

  input.style.backgroundColor = result ? "white" : "red";
  document.getElementById('submit').disabled = result ? false : true;
};

const toggleElements = () => {
  const y = document.getElementById('Y');
  const x = document.getElementById('X');
  const r = document.getElementById('R');
  const submit = document.getElementById('submit');
  y.disabled = !y.disabled;
  x.disabled = !x.disabled;
  r.disabled = !r.disabled; 
  submit.disabled = !submit.disabled;
}
function ajaxSuccess () {
  console.log(this.responseText);
}

function displayData (dataObject) {
  document.getElementById('result').className = 'visible';
  setHTML('result_x', dataObject.x);
  setHTML('result_y', dataObject.y);
  setHTML('result_r', dataObject.r);
  setHTML('result_fits', dataObject.fits);
  setHTML('result_processing_time', dataObject.processing_time);
  setHTML('result_current_time', dataObject.current_time);
}

function setHTML (id, html) {
  document.getElementById(id).innerHTML = html;
}

const submit = () => {
  document.getElementById('result').className = 'invisible';
  const x = document.getElementById('X').value;
  const y = document.getElementById('Y').value;
  const r = document.getElementById('R').innerHTML;
  toggleElements();
  const xhr = sendData({x, y, r});

  xhr.onload = function () {
      toggleElements();
      const response = JSON.parse(this.response);
      const result = document.getElementById('result');
      displayData(response);

      document.getElementById('result').className = "visible";
      document.getElementById('timeouted-table').className = "invisible";
  };
  xhr.ontimeout = function (event) {
    toggleElements();
    document.getElementById('result').className = "invisible";
    document.getElementById('timeouted-table').className = "visible";
  }
}

function sendData (data) {
  const xhr = new XMLHttpRequest();
  xhr.open('POST', 'http://localhost:8080/result.php');
  xhr.timeout = 100;

  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

  xhr.send(encodeObject(data));
  return xhr;
};

function encodeObject (object) {
  const encodedString = '';
  const ownProperties = [];
  for (property in object) {
    if (object.hasOwnProperty(property)) {
      ownProperties.push(`${property}=${object[property]}`);
    }
  }
  return ownProperties.join('&');
}