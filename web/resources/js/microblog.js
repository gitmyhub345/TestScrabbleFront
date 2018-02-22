$(document).ready(function(){
        $(document).on('click','.searchpost',function(event){
            var u = 'posts/ajax/api/getUserPosts/'+$(event.target).attr('data-link');
            var targettext = $(event.target).attr('data-link');
//            alert('dyanmic anchor clicked '+targettext +'\n'+u);
            $('#postListing').text('');
            
            $.ajax({
                url:    u,
                type:   'GET',
                dataType: 'json'
            }).done(function(json){
                $('#postListing').append("Here are "+json[0].nickname+"'s posts:<br>");
                for (var i = 0; i < json.length; i++){
                    $('#postListing').append('<label>'+json[i].postBody+'</label><br>');
                    
                }
            });
        });
        
        $('.hoveredit').click(function(event){
            //alert('edit or delete image clicked'+'\n'+$(event.target).attr('data-link')+'\n'+$(event.target).prop('tagName'));
            var ident = $(event.target).attr('data-link');
            var u ='posts/ajax/api/'+ident.split(' ')[0]+'/'+ident.split(' ')[1];
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var divId = "post"+ident.split(' ')[1];
//            alert(u);
            if(ident.split(' ')[0] == 'delete') {
                var response = confirm("are you sure you wish to delete this post?");
                if (response){
                    $.ajax({
                        url: u,
                        type: 'POST',
                        dataType: 'text',
                        beforeSend: function( xhr, options){
                            xhr.setRequestHeader(header, token);
                        }
                    }).done(function(t){
                        $('.msg').text(t);
                        if(t){
                            $('#'+ divId).remove();
                        }
                    });
                }
            }else{
                $('#PostModalForm').show();
                $('#PostForm').attr('action','/DragonWorld/posts/edit/'+ident.split(' ')[1]);
//                alert($('#PostForm').attr('action'));
                $('#post').val($('#post'+ident.split(' ')[1]).children('label:last-of-type').text());
            }
            
        });
	$('#searchuser').click(function(){
                var u = 'posts/ajax/api/getUsers/'+$('#search').val();
//                alert(u);
		$.ajax({
			url: u,
			type: 'GET',
			dataType: 'json'
		}).done(function(json){
                //    alert(json.length);
                    $('#usersearchlist').text('');
                    if (json.length > 0){
			for (var i = 0; i <json.length;i++){
				$('#usersearchlist').append('<img src="/DragonWorld/resources/images/profile/'+json[i].avatar+'" width="30dp" height="30dp">'
                                        +'<a class="searchpost" href="#" data-link="'+json[i].userID+'">' +json[i].nickname+'</a><br>');
			}
                    } else {
                        $('#usersearchlist').append('no user found with your search criteria.');
                    }
		});
	});
	$('#dl').click(function(e){
		e.preventDefault();  //stop the browser from following
		window.location.href = 'static/img/profile/goldavatar.png';
	});
	$('.menu').click(function() {
		$('#menu').toggle();
		//document.getElementById('menu').classList.toggle('show');
	});

	$('#profile').click(function() {
		$('#menu').css('z-index', '1000');
		$('#menu').toggle();
		//document.getElementById('menu').classList.toggle('show');

		//alert('image clicked');
	});
	
        $('#addPost').click(function(){
            $('#PostModalForm').show();
        })
        $('.close').click(function(){
            $('.ModalForm').hide();
        });
        $('#ModalFormCancel').click(function(){
            $('.ModalForm').hide();
        });
	
	$('#login').click( function(){
		//alert("popup");
		$('#loginModalForm').show();
	});
	$('#close').click( function(){
		$('#loginModalForm').hide();
	});
	$('#ModalFormCancel').click(function(){
		$('#loginModalForm').hide();
	});
	$(window).click(function (event){
		if(event.target.id === $('#loginModalForm').attr('id')){
			$('#loginModalForm').hide();
		}
	});
	/*
	btn.onClick = function(){
		modal.style.display = "block";
	}
	
	span.onClick = function(){
		modal.style.display = "none";
	}
	window.onClick = function(event){
		if (event.target == modal){
			modal.style.display = "none";
		}
	}
*/
});
