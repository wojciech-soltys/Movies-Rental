/**
 * plugin.js
 *
 * Released under LGPL License.
 * Copyright (c) 1999-2015 Ephox Corp. All rights reserved
 *
 * License: http://www.tinymce.com/license
 * Contributing: http://www.tinymce.com/contributing
 */

/*global tinymce:true */
/*eslint consistent-this:0 */

tinymce.PluginManager.add('textcolor', function(editor) {
	var cols, rows;

	rows = editor.settings.textcolor_rows || 3;
	cols = editor.settings.textcolor_cols || 6;

	function getCurrentColor(format) {
		var color;

		editor.dom.getParents(editor.selection.getStart(), function(elm) {
			var value;

			if ((value = elm.style[format == 'forecolor' ? 'color' : 'background-color'])) {
				color = value;
			}
		});

		return color;
	}

	function mapColors() {
		var i, colors = [], colorMap;

		colorMap = editor.settings.textcolor_map || [
            "333333", "Black",
			"464547", "Dark Gray",
			"666666", "Medium Gray",
			"999999", "Gray",
			"cccccc", "Light Gray",
            "ffffff", "White",

			"8e244d", "Plum",
			"b22746", "Raspberry",
			"cB2323", "Red",
            "e4445f", "Fuchsia",
            "ff9b19", "Orange",
			"ffdc19", "Yellow",

            "7f993a", "Dark Green",
            "a3c644", "Lime Green",
            "39c2d7", "Blue",
            "1a9cb0", "Dark Blue",
            "8b5a9f", "Violet",
		];

		for (i = 0; i < colorMap.length; i += 2) {
			colors.push({
				text: colorMap[i + 1],
				color: '#' + colorMap[i]
			});
		}

		return colors;
	}

	function renderColorPicker() {
		var ctrl = this, colors, color, html, last, x, y, i, id = ctrl._id, count = 0;

		function getColorCellHtml(color, title) {
			var isNoColor = color == 'transparent';

			return (
				'<td class="mce-grid-cell' + (isNoColor ? ' mce-colorbtn-trans' : '') + '">' +
					'<div id="' + id + '-' + (count++) + '"' +
						' data-mce-color="' + (color ? color : '') + '"' +
						' role="option"' +
						' tabIndex="-1"' +
						' style="' + (color ? 'background-color: ' + color : '') + '"' +
						' title="' + tinymce.translate(title) + '">' +
						(isNoColor ? '&#215;' : '') +
					'</div>' +
				'</td>'
			);
		}

		colors = mapColors();
		colors.push({
			text: tinymce.translate("No color"),
			color: "transparent"
		});

		html = '<table class="mce-grid mce-grid-border mce-colorbutton-grid" role="list" cellspacing="0"><tbody>';
		last = colors.length - 1;

		for (y = 0; y < rows; y++) {
			html += '<tr>';

			for (x = 0; x < cols; x++) {
				i = y * cols + x;

				if (i > last) {
					html += '<td></td>';
				} else {
					color = colors[i];
					html += getColorCellHtml(color.color, color.text);
				}
			}

			html += '</tr>';
		}

		if (editor.settings.color_picker_callback) {
			html += (
				'<tr>' +
					'<td colspan="' + cols + '" class="mce-custom-color-btn">' +
						'<div id="' + id + '-c" class="mce-widget mce-btn mce-btn-small mce-btn-flat" ' +
							'role="button" tabindex="-1" aria-labelledby="' + id + '-c" style="width: 100%">' +
							'<button type="button" role="presentation" tabindex="-1">' + tinymce.translate('Custom...') + '</button>' +
						'</div>' +
					'</td>' +
				'</tr>'
			);

			html += '<tr>';

			for (x = 0; x < cols; x++) {
				html += getColorCellHtml('', 'Custom color');
			}

			html += '</tr>';
		}

		html += '</tbody></table>';

		return html;
	}

	function applyFormat(format, value) {
		editor.undoManager.transact(function() {
			editor.focus();
			editor.formatter.apply(format, {value: value});
			editor.nodeChanged();
		});
	}

	function removeFormat(format) {
		editor.undoManager.transact(function() {
			editor.focus();
			editor.formatter.remove(format, {value: null}, null, true);
			editor.nodeChanged();
		});
	}

	function onPanelClick(e) {
		var buttonCtrl = this.parent(), value;

		function selectColor(value) {
			buttonCtrl.hidePanel();
			buttonCtrl.color(value);
			applyFormat(buttonCtrl.settings.format, value);
		}

		function resetColor() {
			buttonCtrl.hidePanel();
			buttonCtrl.resetColor();
			removeFormat(buttonCtrl.settings.format);
		}

		function setDivColor(div, value) {
			div.style.background = value;
			div.setAttribute('data-mce-color', value);
		}

		if (tinymce.DOM.getParent(e.target, '.mce-custom-color-btn')) {
			buttonCtrl.hidePanel();

			editor.settings.color_picker_callback.call(editor, function(value) {
				var tableElm = buttonCtrl.panel.getEl().getElementsByTagName('table')[0];
				var customColorCells, div, i;

				customColorCells = tinymce.map(tableElm.rows[tableElm.rows.length - 1].childNodes, function(elm) {
					return elm.firstChild;
				});

				for (i = 0; i < customColorCells.length; i++) {
					div = customColorCells[i];
					if (!div.getAttribute('data-mce-color')) {
						break;
					}
				}

				// Shift colors to the right
				// TODO: Might need to be the left on RTL
				if (i == cols) {
					for (i = 0; i < cols - 1; i++) {
						setDivColor(customColorCells[i], customColorCells[i + 1].getAttribute('data-mce-color'));
					}
				}

				setDivColor(div, value);
				selectColor(value);
			}, getCurrentColor(buttonCtrl.settings.format));
		}

		value = e.target.getAttribute('data-mce-color');
		if (value) {
			if (this.lastId) {
				document.getElementById(this.lastId).setAttribute('aria-selected', false);
			}

			e.target.setAttribute('aria-selected', true);
			this.lastId = e.target.id;

			if (value == 'transparent') {
				resetColor();
			} else {
				selectColor(value);
			}
		} else if (value !== null) {
			buttonCtrl.hidePanel();
		}
	}

	function onButtonClick() {
		var self = this;

		if (self._color) {
			applyFormat(self.settings.format, self._color);
		} else {
			removeFormat(self.settings.format);
		}
	}

	editor.addButton('forecolor', {
		type: 'colorbutton',
		tooltip: 'Text color',
		format: 'forecolor',
		panel: {
			role: 'application',
			ariaRemember: true,
			html: renderColorPicker,
			onclick: onPanelClick
		},
		onclick: onButtonClick
	});

	editor.addButton('backcolor', {
		type: 'colorbutton',
		tooltip: 'Background color',
		format: 'hilitecolor',
		panel: {
			role: 'application',
			ariaRemember: true,
			html: renderColorPicker,
			onclick: onPanelClick
		},
		onclick: onButtonClick
	});
});
