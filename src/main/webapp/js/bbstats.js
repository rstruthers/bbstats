$(document).ready(function(){

	$("#logout").click(function(){
		$( "#signout-form" ).submit();
	});
	
	$('#season').change(
			function() {
				url = '/bbstats/v1/seasons/' + $(this).val()  + '/teams';
				$.getJSON(url, {
					ajax : 'true'
				}, function(data) {
					var html = '<option value="">Select</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						html += '<option value="' + data[i].name + '">'
								+ data[i].name + '</option>';
					}
					html += '</option>';
	 
					$('#team').html(html);
				});
			});	

	});