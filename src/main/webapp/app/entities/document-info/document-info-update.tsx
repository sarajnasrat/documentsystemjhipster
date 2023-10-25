/* eslint-disable @typescript-eslint/no-misused-promises */
import React, { useEffect, useRef, useState } from 'react';
import { useForm, Controller, useFormState } from 'react-hook-form';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { InputText } from 'primereact/inputtext';
import { Translate, openFile, byteSize, TextFormat, translate } from 'react-jhipster';
import { FileUpload, FileUploadSelectEvent } from 'primereact/fileupload';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { createEntity, getEntity, updateEntity, reset } from './document-info.reducer';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';

import DatePicker, { DateObject } from 'react-multi-date-picker';
import { Calendar } from 'react-multi-date-picker';
import arabic from 'react-date-object/calendars/arabic';
import arabic_fa from 'react-date-object/locales/arabic_fa';
import { format } from 'date-fns';
import arabic_ar from 'react-date-object/locales/arabic_ar';
import arabic_en from 'react-date-object/locales/arabic_en';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

type FormData = {
  number: string;
  subject: string;
  registeredNumber: string;
  issuedate: Date;
  content: string;
  organization: string;
  dpriority: string;
  scanPath: FileList | null;
};
const priorityOptions = [
  { label: <Translate contentKey="documentmanagementsytemApp.documentInfo.normal">Save</Translate>, value: 'confidential' },
  { label: <Translate contentKey="documentmanagementsytemApp.documentInfo.confidential">Save</Translate>, value: 'most_confidential' },
  { label: <Translate contentKey="documentmanagementsytemApp.documentInfo.mostConfidential">Save</Translate>, value: 'normal' },
];
export const DocumentInfoUpdate = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();
  const isNew = id === undefined;
  const { control, handleSubmit } = useForm<FormData>();
  const documentInfoEntity = useAppSelector(state => state.documentInfo.entity);
  const updating = useAppSelector(state => state.documentInfo.updating);
  const fileuploadRef = useRef(null);
  const [selectedImage, setSelectedImage] = useState<string | null>(null); // Initialize with null
  const [selectedImageContentType, setSelectedImageContentType] = useState<string | null>(null); // Initialize with null
  const [formError, setFormError] = useState<string | null>(null);
  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, [id]);

  const handleImageChange = (event: FileUploadSelectEvent) => {
    const selectedFile = event.files[0];
    setSelectedImageContentType(selectedFile.type);
    const reader = new FileReader();
    reader.onloadend = () => {
      setSelectedImage((reader.result as string).split(',')[1]);
    };
    reader.readAsDataURL(selectedFile);
  };

  const saveEntity = async (data: FormData) => {
    const georgianDate = format(new Date(data.issuedate), 'yyyy-MM-dd');
    console.log(georgianDate);

    console.log(data.issuedate);
    const entity = {
      ...documentInfoEntity,
      ...data,
      scanPath: selectedImage, // Updated the property name
      scanPathContentType: selectedImageContentType,
      issuedate: georgianDate,
      // Updated the property name
    };
    if (isNew) {
      await dispatch(createEntity(entity));
    } else {
      await dispatch(updateEntity(entity));
    }
  };
  const defaultValues = () => {
    if (isNew) {
      return {};
    } else {
      return {
        number: documentInfoEntity.number || '',
        registeredNumber: documentInfoEntity.registeredNumber || '',
        issuedate: documentInfoEntity.issuedate || '',
        subject: documentInfoEntity.subject || '',
        dpriority: documentInfoEntity.dpriority || '',
        scanPath: null, // Set scanPath to null as it's handled separately
        content: documentInfoEntity.content || '',
        organization: documentInfoEntity.organization || '',
      };
    }
  };

  useEffect(() => {
    if (!isNew) {
      setSelectedImage(documentInfoEntity.scanPath);
      setSelectedImageContentType(documentInfoEntity.scanPathContentType);
      const fileObject = new File([], 'Previous file', { type: documentInfoEntity.scanPathContentType });
      fileObject['objectURL'] = `data:${documentInfoEntity.scanPathContentType};base64,${documentInfoEntity.scanPath}`;
      const fileArray = [fileObject];
      fileuploadRef.current?.setFiles(fileArray); // Added a null check
    }
  }, [documentInfoEntity]);
  const handleClose = () => {
    navigate('/document-info' + documentInfoEntity.id);
  };
  return (
    <div>
      <Row className="justify-content-center">
        <Col md="12">
          <h2 id="documentmanagementsytemApp.documentInfo.home.createOrEditLabel" data-cy="DocumentInfoCreateUpdateHeading">
            {translate('documentmanagementsytemApp.documentInfo.home.createOrEditLabel')}
          </h2>
        </Col>
      </Row>
      <form onSubmit={handleSubmit(saveEntity)}>
        {formError && <div className="alert alert-danger">{formError}</div>}
        <Row>
          {/* <div>
              {!isNew ? (
                <div>
                  <label htmlFor="document-info-id">ID</label>
                  <Controller
                    name="id"
                    control={control}
                    render={({ field }) => (
                      <InputText {...field} readOnly={true} className="form-control" id="document-info-id" />
                    )}
                  />
                </div>
              ) : null}
            </div> */}
          <Col sm="12" md="4">
            <div>
              <label htmlFor="document-info-number" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.number')}
              </label>
              <Controller
                name="number"
                defaultValue={defaultValues().number}
                control={control}
                rules={{ required: { value: true, message: translate('entity.validation.required') } }}
                // rules={{ required: 'Number is required' }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                  <>
                    <InputText type="text" value={value} onChange={onChange} className="form-control" id="document-info-number" />
                    <span>{error && <div className="error">{error.message}</div>}</span>
                  </>
                )}
              />
            </div>
            <div>
              <label htmlFor="document-info-registeredNumber" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.registeredNumber')}
              </label>
              <Controller
                name="registeredNumber"
                defaultValue={defaultValues().registeredNumber}
                control={control}
                rules={{ required: 'Register Number is required' }}
                render={({ field }) => (
                  <>
                    {' '}
                    <InputText {...field} className="form-control" id="document-info-registeredNumber" />
                    {/* <p className="text-danger">{errors.number && errors.registeredNumber.message}</p> Display error message */}
                  </>
                )}
              />
            </div>

            <div>
              <label htmlFor="document-info-organization" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.organization')}
              </label>
              <Controller
                name="organization"
                control={control}
                defaultValue={defaultValues().organization}
                rules={{ required: 'organization is required' }}
                render={({ field }) => (
                  <>
                    {/* <p className="text-danger">{errors.number && errors.organization.message}</p> Display error message */}
                    <InputText {...field} className="form-control" id="document-info-organization" />
                  </>
                )}
              />
            </div>
            <div>
              <label htmlFor="document-info-issuedate" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.issuedate')}
              </label>
              <Controller
                control={control}
                name="issuedate"
                defaultValue={defaultValues().issuedate}
                rules={{ required: true }}
                render={({ field: { onChange, value }, fieldState: { invalid, isDirty }, formState: { errors } }) => (
                  <>
                    <DatePicker
                      className="form-control"
                      calendar={arabic}
                      locale={arabic_ar}
                      value={value || ''}
                      onChange={date => {
                        if (date instanceof DateObject) {
                          // Access the date properties to get the selected date components
                          const year = date.year;
                          const month = date.month;
                          const day = date.day;

                          // Construct the date in the desired format and assign it to onChange
                          const selectedDate = `${year}-${month}-${day}`;
                          onChange(selectedDate);
                        } else {
                          onChange(''); // Set to an empty string if date is invalid
                        }
                      }}
                    />
                    <div>{errors && errors.issuedate && errors.issuedate.type === 'required' && <span>{'Date is Required'}</span>}</div>
                  </>
                )}
              />
            </div>
            <div>
              <label htmlFor="document-info-dpriority" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.dpriority')}
              </label>
              <Controller
                name="dpriority"
                control={control}
                defaultValue={defaultValues().dpriority}
                rules={{ required: 'Priority is required' }}
                render={({ field: { onChange, value }, fieldState: { error } }) => (
                  <div>
                    <Dropdown
                      filter
                      value={value}
                      options={priorityOptions}
                      onChange={e => onChange(e.value)}
                      placeholder={translate('documentmanagementsytemApp.documentInfo.selectPriority')}
                      className="w-full md:w-20rem"
                    />
                    {error && <div className="error text-danger">{error.message}</div>}
                  </div>
                )}
              />
            </div>
            <div className="mt-5">
              <Button tag={Link} icon="pi pi-back" to="/document-info" replace color="info">
                <FontAwesomeIcon icon="arrow-left" /> <Translate contentKey="entity.action.back">Save</Translate>
              </Button>
              <Button color="primary" icon="pi pi-save" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" /> <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </div>
          </Col>
          <Col md="4" sm="12">
            <div>
              <label htmlFor="document-info-subject" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.subject')}
              </label>
              <Controller
                name="subject"
                defaultValue={defaultValues().subject}
                control={control}
                rules={{ required: 'subject is requird' }}
                render={({ field }) => (
                  <>
                    {/* <p className="text-danger">{errors.number && errors.subject.message}</p> Display error message */}
                    <InputText {...field} className="form-control" id="document-info-subject" />
                  </>
                )}
              />
            </div>

            <div>
              <label htmlFor="document-info-content" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.content')}
              </label>
              <Controller
                name="content"
                control={control}
                defaultValue={defaultValues().content}
                rules={{ required: 'content is required' }}
                render={({ field }) => (
                  <>
                    <InputTextarea {...field} autoResize rows={10} cols={30} className="form-control" />
                    {/* <InputText {...field} className="form-control" id="document-info-content" /> */}
                  </>
                )}
              />
            </div>
          </Col>
          <Col md="4" sm="12">
            <div>
              <label htmlFor="document-info-scanPath" className="form-label">
                {translate('documentmanagementsytemApp.documentInfo.scanPath')}
              </label>
              <FileUpload
                name="scanPath"
                url={'/api/upload'}
                // chooseOptions={chooseOptions}
                multiple={false}
                onSelect={handleImageChange}
                chooseLabel={translate('global.selectFile')}
                accept="image/*"
                auto
                maxFileSize={1000000}
                emptyTemplate={
                  selectedImage && (
                    <img
                      src={`data:${selectedImageContentType};base64,${selectedImage}`}
                      alt="Selected"
                      style={{ maxWidth: '100px', maxHeight: '100px', marginTop: '5px', textAlign: 'center' }}
                    />
                  )
                }
              />
            </div>
          </Col>
        </Row>
      </form>
    </div>
  );
};

export default DocumentInfoUpdate;
