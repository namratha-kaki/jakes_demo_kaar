import { Component, OnDestroy, HostListener } from '@angular/core';
import { ParagraphComponent, SupplementHashAnchorsPipe  } from "@spartacus/storefront";
import { Router, ActivatedRoute } from '@angular/router';
import { CmsComponentData } from "@spartacus/storefront";
import { CmsParagraphComponent } from '@spartacus/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { Subscription  } from "rxjs";

@Component({
  selector: 'cx-jakes-paragraph',
  templateUrl: './jakes-paragraph.component.html',
  styleUrls: ['./jakes-paragraph.component.scss'],
  providers: [SupplementHashAnchorsPipe]
})
export class JakesParagraphComponent extends ParagraphComponent implements OnDestroy {

  content: SafeHtml = '';
  private dataSubscription: Subscription;

  constructor(component: CmsComponentData<CmsParagraphComponent>,
    router: Router,
    private sanitizer: DomSanitizer,
    private supplementHashAnchorsPipe: SupplementHashAnchorsPipe) {
    super(component, router);
    this.dataSubscription = component.data$.subscribe((data) => {
      const supplementedContent = this.supplementHashAnchorsPipe.transform(data.content);
      const html = '<p>' + (supplementedContent ?? '') + '</p>';
      this.content = this.sanitizer.bypassSecurityTrustHtml(html);
    });
  }

  ngOnDestroy() {
    if (this.dataSubscription) {
      this.dataSubscription.unsubscribe();
    }
  }

  @HostListener('click', ['$event'])
  public handleTheClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (target.tagName.toLowerCase() === 'b') {
      const anchorElement = target.closest('a');
      if (anchorElement) {
        const href = anchorElement.getAttribute('href');
        try {
          const url = new URL(href || '');
          if (url.hash) {
            event.preventDefault();
            const targetId = url.hash.substring(1);

            // Get the target element by its id
            const targetElement = document.getElementById(targetId);
            // Scroll to the target element smoothly
            if (targetElement) {
              targetElement.scrollIntoView({ behavior: 'smooth' });
            }
          }
        } catch (e) {
          // If there's an error parsing the URL, just ignore and continue with default behavior
        }
      }
    }
  }

}
