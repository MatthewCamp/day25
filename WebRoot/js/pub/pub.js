String.prototype.trim=function(){
	var p = /^\s*/;
	var str = this.replace(p,"");
	p = /\s*$/;
	str = str.replace(p,"");
	return str;
};