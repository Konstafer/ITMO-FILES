/* Изначально форма не заполнена и по этому считаем что все возможные ошибки есть  */
var errorNull = true, errorText = true, errorText2 = true;

/* Для удобства и уменьшения размера кода выносим функцию проверки поля на null в отдельную переменную */
var checkNull = function(){
  $(this).val($(this).val().trim());
  if ($(this).val() =="") {
    /* Выводим сообщение об ошибке под элементом.
       This в данном случае это элемент, который инициировал вызов функции */
    $(this).notify("Field must be filled in", "error"); 
    $(this).addClass("errtextbox");
    errorNull = true;
  } else {
    errorNull = false;
    $(this).removeClass("errtextbox");
  }
};

/* Проверяем значения полей Имя и Информация на null в момент когда они теряют фокус */
$("#y").focusout(checkNull);
$("#r").focusout(checkNull);

/* Проверяем корректность поля "y" */
$("#y").focusout(function(){
  var value = $(this).val();
  if (value.search(/^[-]?[0-9]+[\.\,]?[0-9]*$/) != 0 || value < -5 || value > 5) { 
    $(this).notify("Please enter in the range {-5; 5} and use '.' to split the fractional part", "info");
    $(this).addClass("errtextbox");
    errorText = true;
  } else { 
    $(this).removeClass("errtextbox");
    errorText = false;
  }
});

/* Проверяем корректность поля "r" */
$("#r").focusout(function(){
  var value = $(this).val();
  if (value.search(/^[0-9]+[\.\,]?[0-9]*$/) != 0 || value < 2 || value > 5) { 
    $(this).notify("Please enter in the range {2; 5} and use '.' to split the fractional part", "info");
    $(this).addClass("errtextbox");
    errorText2 = true;
  } else { 
    $(this).removeClass("errtextbox");
    errorText2 = false;
  }
});


/* В результате клика по кнопке отправить если ошибок заполнения нет то форма отправляется иначе получаем сообщение об ошибке */
$("#send").click(function(){
  if (!(errorNull || errorText || errorText2)) {
    $("#sendForm").submit();
  } else {
    $(this).notify("Form is empty or not filled correctly", "error");
  }
});