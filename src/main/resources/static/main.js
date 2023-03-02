/**
 * 
 */
 
 $('document').ready(function(){
	
	$('.container.table.btn').on('click', function(event){
		
		event.preventDefault();
		
		$('#editModal').modal();
		
	});
	
	
});