
$(document).ready(function() {
	document.getElementById("pta-haku-input-search-text").focus();

	$('#pta-haku-input-container input').keypress(function (e) {
		if (e.which == 13) {
			teeHaku();
		}
	});

	$('#pta-haku-input-container button').click(function() { teeHaku(); });

	const lang = 'FI';
	var pageSize = 10;

	function teeHaku(skip, facetQuery) {
		skip       = skip || 0;
		facetQuery = facetQuery || {};
		var hakusanat = $('#pta-haku-input-container input').val();


		var query = {
			query: hakusanat.split(/\s+/).filter(function(v) { return v.length > 0; }),
			skip: skip,
			pageSize: pageSize,
			facets: facetQuery
		};

		var vinkit = $('#pta-tulokset #pta-tulokset-vinkit');
		vinkit.hide();

		var osumat = $('#pta-tulokset #pta-tulokset-osumat');
		osumat.hide();

		var fasetit = $('#pta-tulokset #pta-tulokset-fasetit');
		fasetit.hide();

		var virhe = $('#pta-tulokset #pta-tulokset-virhe');
		virhe.hide();

		$.ajax({
			url: 'v1/search',
			method: 'POST',
			data: JSON.stringify(query),
			contentType: 'application/json',
			dataType: 'json'
		}).done(function(result) {


			// Vinkit
			var vinkkiLista = $('#pta-tulokset-vinkit-lista', vinkit);
			vinkkiLista.empty();
			result.hints.forEach(function(vinkki) {
				var tmp = $('<div></div>');
				tmp.addClass('pta-tulokset-vinkit-vinkki');
				tmp.text(vinkki);
				tmp.click(function() {
				    var text = $('#pta-haku-input-container input').val();
				    text += ' ';
				    text += vinkki;
				    $('#pta-haku-input-container input').val(text);
				    teeHaku(0);
				    
				});
				vinkkiLista.append(tmp);
			});
			vinkit.show();

			// Tulokset
			var osumaLista = $('#pta-tulokset-osumat-lista', osumat);
			osumaLista.empty();
			result.hits.forEach(function(osuma) {
				var tmp = $('<div></div>');
				tmp.addClass('pta-tulokset-osumat-osuma');
				
				var title = $('<p></p>');
				var text = osuma.text.find( d => d.lang === lang && d.title) || osuma.text[0] || {};
				
				title.text(text.title + ' ('+Math.round(osuma.score*100)/100+')');
				title.addClass('pta-tulokset-osumat-osuma-title');
				tmp.append(title);
				
				var desc = $('<div></div>');
				desc.addClass('pta-tulokset-osumat-osuma-desc');
				desc.text(text.abstractText);
				desc.hide();
				title.click(function() { desc.toggle(); });
				desc.click(function() { desc.toggle(); });
				
				tmp.append(desc);
				
				var link = $('<a></a>');
				link.text('Avaa paikkatietohakemistossa');
				link.attr('href', osuma.url);
				link.attr('target', '_blank');
				
				tmp.append(link);
				
				//tmp.text('foo: '+JSON.stringify(osuma));
				osumaLista.append(tmp);
			});
			
			if (skip > 0) {
				osumaLista.append($('<span class="pta-tulokset-osumat-previous">Edelliset</span>').click(function() {
					teeHaku(skip-pageSize);
				}));
			}

			if ((skip + result.hits.length) < result.totalHits) {
				osumaLista.append($('<span class="pta-tulokset-osumat-next">Seuraavat</span>').click(function() {
					teeHaku(skip+pageSize);
				}));
			}
			
			osumat.show();


			// Fasetit
			var fasettiLista = $('#pta-tulokset-fasetit-lista', fasetit);
			fasettiLista.empty();

			for (var fasetti in result.facets) {
				var div = $('<div></div>');
				div.append($('<h4></h4>').text(fasetti));
				var table = $('<table></table>');
				
				result.facets[fasetti].forEach(function(d) {
					var f = fasetti;
					var row = $('<tr><td>'+d.id+'</td><td>'+d.count+'</td></tr>');
					row.on('click', function() {
						facetQuery[f] = facetQuery[f] || [];
						facetQuery[f].push(d.id);
						teeHaku(skip, facetQuery);
					});
				    table.append(row);
				});
				div.append(table);
				
				fasettiLista.append(div);	
			}
						
			
			fasetit.show();

		}).fail(function(err) {
			virhe.empty();
			console.error(err);
			var tmp = $('<div></div>');
			tmp.addClass('pta-virhe');
			tmp.text('Tapahtui virhe: '+err.statusText);

			var pre = $('<pre></pre>');
			pre.text(err.responseText);
			tmp.append(pre);

			virhe.append(tmp);
			virhe.show();
		});
	}


	//teeHaku();

});
