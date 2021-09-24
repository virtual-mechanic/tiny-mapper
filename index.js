// Version record event getter
const getRecordEvent = (record) => {
    let event = '';
  
    // Event type check
    switch (record.event.type) {
      case 'input':
        // Type check
        if (record.setup.attributes.type === 'radio' || record.setup.attributes.type === 'checkbox') {
          event = 'im-setInputChecked';
        } else if (record.setup.nodeName === 'select') {
          event = 'im-setSelectValue';
        } else {
          event = 'im-setInputValue';
        }
        break;
  
      case 'focus':
        // Type check
        if (record.setup.attributes.type === 'date') {
          event = 'im-setInputDateValue';
        } else {
          event = undefined;
        }
        break;
  
      case 'click':
      case 'explicitClick':
        event = 'im-click';
        break;
  
      default:
    }
  
    return event;
  };

// Version record identity field name setter
const setIdentityField = (field) => {

    const fieldsDeprecated = [
      'dateofbirth',
    ];
    // Same as above but these must be converted
    const fieldsOld = [
      'lastname',
      'zip',
    ];
    const labels = field.match(/[^_]+/g);
  
    return labels.map((label, i) => {
      const newLabel = label.toLowerCase();
      let name = '';
  
      // Format check
      if (i > 0 && !fieldsDeprecated.includes(newLabel)) {
        name = newLabel.charAt(0).toUpperCase() + newLabel.slice(1);
      } else {
        name = newLabel;
  
        // Naming convention check
        if (fieldsOld.includes(newLabel)) {
          // This approach covers larger arrays without additional iterations
          const index = fieldsOld.findIndex((fieldOld) => fieldOld === newLabel);
  
          name = fieldsOld[index];
        }
      }
  
      return name;
    }).join('');
  };

// Version record action props setter
const setRecordActionProps = (action, identity) => {
    let props = {};
  
    // Action event check
    switch (action.event) {
      case 'im-setInputDateValue':
      case 'im-setInputValue':
      case 'im-setSelectValue':
        if (action.identityName === undefined) {
          props = { value: action.value ?? '' };
        }
  
        props = {
          value: identity[action.identityName] ?? action.value,
        };
        break;
  
      case 'im-setInputChecked': {
        props = {
          checked: true,
        };
        break;
      }
  
      default:
    }
  
    return props;
  };

// Version recording template format setter
export const setRecordingTemplate = (id, description, recording) => {
    // Get track of all navigation events
    const indexes = recording.records.map((record, i) => record.event.type === 'navigate' && {
      index: i,
      triggerUrl: record.setup.siteUrl ?? record.setup.url,
    }).filter((record) => record !== false);
    const pages = [];
  
    // Split pages as set of records between two navigation events
    for (let i = 0; i <= indexes.length - 1; i++) {
      pages.push({
        triggerUrl: indexes[i].triggerUrl,
        records: i === indexes.length - 1
          ? recording.records.slice(indexes[i].index)
          : recording.records.slice(indexes[i].index, indexes[i + 1].index),
      });
    }
    
    const eventTypes = ['input', 'explicitClick', 'focus', 'click'];
    const recordingTemplate = {
      id,
      name: description,
      pages: pages.map((page) => (
        {
          target: {
            url: page.triggerUrl,
          },
          actions: page.records.filter((record) => eventTypes.includes(record.event.type))
            .map((record) => {
              const newRecord = {
                event: getRecordEvent(record),
                nodeSelector: {
                  altPath: record.setup.altPath,
                  altSelector: record.setup.altSelector,
                  rootPath: record.setup.rootpath,
                  selector: record.setup.selector,
                  xpath: record.setup.xpath,
                },
                value: record.setup.value ?? record.setup.attributes.value,
              };
  
              // Existing check
              if (record.setup.attributes.dataScFieldtype) {
                newRecord.identityField = setIdentityField(record.setup.attributes.dataScFieldtype);
              }
  
              return newRecord;
            })
            .filter(
              (action) => action.event !== undefined,
            ),
        })),
    };
  
    return {
      ...recordingTemplate,
      pages: recordingTemplate.pages.filter((page) => !page.target.url.includes('chrome://newtab')
      && page.actions.length > 0)
        .map((page) => ({
          ...page,
          actions: page.actions.map((action) => ({
            ...action,
            ...setRecordActionProps(action, recording.identity),
          })),
        })),
    };
  };
