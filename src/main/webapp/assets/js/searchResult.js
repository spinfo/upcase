$(document)
		.ready(
				function() {
					$("#volumeSelection")
							.change(
									function() {
										var volume = $(
												"#volumeSelection option:selected")
												.text();
										var url = "http://localhost:8080/drc/chapters/by/volume/title/"
												+ volume;
										// console.log(url);
										$
												.get(
														url,
														function(data) {
															var options = "";
															for (var i = 0; i < data.length; i++) {
																options += "<option value='"
																		+ data[i]
																		+ "'>"
																		+ data[i]
																		+ "</option>";
															}
															// console.log(options);
															$(
																	"#chapterSelection")
																	.empty()
																	.html(
																			options);
														});
									});

					var volumeSort = "asc";
					var url = "http://localhost:8080/drc/searchResult?";

					$("#sortVolume")
							.click(
									function(event) {
										event.preventDefault();
										// glyphicon
										// glyphicon-sort-by-alphabet-alt

										var searchPhrase = getURLParameter("searchPhrase");
										var volumeSort = getURLParameter("volumeSort");
										var page = getURLParameter("page");
										if (typeof page == 'undefined') {
											page = 1;
										}
										var _sortImg = $(this).children()
												.first();
										if (volumeSort == "asc") {
											volumeSort = "des";
											_sortImg
													.removeClass(
															"glyphicon-sort-by-alphabet")
													.addClass(
															"glyphicon-sort-by-alphabet-alt");
										} else {
											volumeSort = "asc";
											_sortImg
													.removeClass(
															"glyphicon-sort-by-alphabet-alt")
													.addClass(
															"glyphicon-sort-by-alphabet");
										}
										// volumeSort = (volumeSort == "asc") ?
										// "des" : "asc";
										// window.location = url +
										// "searchPhrase=" + searchPhrase
										// + "&" + "page=" + page + "&" +
										// "volumeSort="
										// + volumeSort;
									});

				});

function getURLParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) {
			return sParameterName[1];
		}
	}
}