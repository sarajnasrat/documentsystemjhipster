import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DocumentInfo from './document-info';
import DocumentInfoDetail from './document-info-detail';
import DocumentInfoUpdate from './document-info-update';
import DocumentInfoDeleteDialog from './document-info-delete-dialog';

const DocumentInfoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DocumentInfo />} />
    <Route path="new" element={<DocumentInfoUpdate />} />
    <Route path=":id">
      <Route index element={<DocumentInfoDetail />} />
      <Route path="edit" element={<DocumentInfoUpdate />} />
      <Route path="delete" element={<DocumentInfoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DocumentInfoRoutes;
