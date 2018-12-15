
export default {
    
    addValidityListener(document, componentId, message) {
    document.getElementById(componentId).addEventListener('invalid', function(e) {
        e.target.setCustomValidity(message); 
          
        document.getElementById(componentId).addEventListener('input', function(e){
          e.target.setCustomValidity('');
        });
      }, false);
  },

  addValidityListeners(document, componentIds, message) {
      for(var id of componentIds)
      {
        this.addValidityListener(document, id, message);
    }
  },

  removeValidityListener(document, componentId, message) {
    document.getElementById(componentId).removeEventListener('invalid', function(e) {
        e.target.setCustomValidity(message); 
          
        document.getElementById(componentId).removeEventListener('input', function(e){
          e.target.setCustomValidity('');
        });
      }, false);
  },

  removeValidityListeners(document, componentIds, message) {
    for(var id of componentIds)
    {
      this.removeValidityListener(document, id, message);
  }
},
}
