import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document-info.reducer';

export const DocumentInfoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentInfoEntity = useAppSelector(state => state.documentInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentInfoDetailsHeading">
          <Translate contentKey="documentmanagementsytemApp.documentInfo.detail.title">DocumentInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.id}</dd>
          <dt>
            <span id="number">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.number">Number</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.number}</dd>
          <dt>
            <span id="registeredNumber">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.registeredNumber">Registered Number</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.registeredNumber}</dd>
          <dt>
            <span id="issuedate">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.issuedate">Issuedate</Translate>
            </span>
          </dt>
          <dd>
            {documentInfoEntity.issuedate ? (
              <TextFormat value={documentInfoEntity.issuedate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="subject">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.subject">Subject</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.subject}</dd>
          <dt>
            <span id="dpriority">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.dpriority">Dpriority</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.dpriority}</dd>
          <dt>
            <span id="scanPath">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.scanPath">Scan Path</Translate>
            </span>
          </dt>
          <dd>
            {documentInfoEntity.scanPath ? (
              <div>
                {documentInfoEntity.scanPathContentType ? (
                  <a onClick={openFile(documentInfoEntity.scanPathContentType, documentInfoEntity.scanPath)}>
                    <img
                      src={`data:${documentInfoEntity.scanPathContentType};base64,${documentInfoEntity.scanPath}`}
                      style={{ maxHeight: '100px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {documentInfoEntity.scanPathContentType}, {byteSize(documentInfoEntity.scanPath)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="content">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.content">Content</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.content}</dd>
          <dt>
            <span id="organization">
              <Translate contentKey="documentmanagementsytemApp.documentInfo.organization">Organization</Translate>
            </span>
          </dt>
          <dd>{documentInfoEntity.organization}</dd>
        </dl>
        <Button tag={Link} to="/document-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document-info/${documentInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentInfoDetail;
