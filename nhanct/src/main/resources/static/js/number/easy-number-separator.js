$(document).ready(function () {
  $(document).on('input', '.number-separator', function (e) {
    if (/^[0-9.,]+$/.test($(this).val())) {
      $(this).val(
        parseFloat($(this).val().replace(/,/g, '')).toLocaleString('en')
      );
    } else {
      $(this).val(
        $(this)
          .val()
          .substring(0, $(this).val().length - 1)
      );
    }
  });

  var a = document.getElementById('custom-no-file-chosen');
  if(a.value == "")
  {
    fileLabel.innerHTML = "Choose file 2";
  }
  else
  {
    var theSplit = a.value.split('\\');
    fileLabel.innerHTML = theSplit[theSplit.length-1];
  }
});
