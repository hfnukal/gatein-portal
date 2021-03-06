/*
 * Copyright (C) 2011 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.portal.mop.importer;

import java.util.Arrays;

import org.exoplatform.portal.config.DataStorage;
import org.exoplatform.portal.config.model.Page;
import org.exoplatform.portal.mop.page.PageContext;
import org.exoplatform.portal.mop.page.PageKey;
import org.exoplatform.portal.mop.page.PageService;
import org.exoplatform.portal.mop.page.PageState;

/**
 * @author <a href="trongtt@gmail.com">Trong Tran</a>
 * @version $Revision$
 */
public class PageImporter
{
   /** . */
   private final Page src;

   /** . */
   private final DataStorage service;

   /** . */
   private final PageService pageService;

   /** . */
   private final ImportMode mode;

   public PageImporter(ImportMode importMode, Page page, DataStorage dataStorage_, PageService pageService)
   {
      this.mode = importMode;
      this.src = page;
      this.service = dataStorage_;
      this.pageService = pageService;
   }

   public void perform() throws Exception
   {
      PageContext existingPage = pageService.loadPage(src.getPageKey());
      Page dst;

      //
      switch (mode)
      {
         case CONSERVE :
            dst = null;
            break;
         case INSERT :
            if (existingPage == null)
            {
               dst = src;
            }
            else
            {
               dst = null;
            }
            break;
         case MERGE :
         case OVERWRITE :
            dst = src;
            break;
         default :
            throw new AssertionError();
      }

      if (dst != null)
      {
         PageState dstState =
            new PageState(
               dst.getTitle(), 
               dst.getDescription(), 
               dst.isShowMaxWindow(), 
               dst.getFactoryId(),
               dst.getAccessPermissions() != null ? Arrays.asList(dst.getAccessPermissions()) : null,
               dst.getEditPermission());

         pageService.savePage(new PageContext(src.getPageKey(), dstState));
         service.save(dst);
      }
   }
}
